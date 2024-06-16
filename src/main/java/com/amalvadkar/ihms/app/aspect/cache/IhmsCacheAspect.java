package com.amalvadkar.ihms.app.aspect.cache;

import com.amalvadkar.ihms.app.client.RedisCacheClient;
import com.amalvadkar.ihms.app.models.dto.CacheMetaData;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class IhmsCacheAspect {
    private static final String CONTENT_CACHED_RESPONSE_HEADER_KEY = "Content-Cached";
    private static final String CACHE_MISS_RESPONSE_HEADER_VALUE = "cache-miss";
    private static final String CACHE_HIT_RESPONSE_HEADER_VALUE = "cache-hit";
    public static final String X_REQUEST_HASH_HEADER_KEY = "X-Request-Hash";

    private final RedisCacheClient redisCacheClient;

    @Around("@annotation(com.amalvadkar.ihms.app.aspect.cache.IhmsCache)")
    public Object ihmsCacheAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.debug("Inside ihmsCacheAround");

        String requestHash = getRequestHashFromHeader();

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        log.debug(signature.getName());
        Method method = signature.getMethod();

        IhmsCache ihmsCache = method.getAnnotation(IhmsCache.class);

        String cacheKeyPrefix = ihmsCache.cacheKeyPrefix().getPrefix();
        String expiryTimeInSec = ihmsCache.expiryTimeInSec();
        log.debug("Request hash -> {}" , requestHash);
        log.debug("Cache key prefix -> {}" , cacheKeyPrefix);
        log.debug("Expiry time in sec -> {}" , expiryTimeInSec);
        CacheMetaData cacheMetaData = new CacheMetaData(requestHash, cacheKeyPrefix, expiryTimeInSec);

        String cachedResponse = redisCacheClient.getCachedResponse(cacheMetaData);
        if (Objects.nonNull(cachedResponse)){
            log.debug("Cache found, loading from cache");
            redisCacheClient.incrementCacheHitInAsync(cacheMetaData);
            return ResponseEntity
                    .ok().header(CONTENT_CACHED_RESPONSE_HEADER_KEY, CACHE_HIT_RESPONSE_HEADER_VALUE)
                    .body(cachedResponse);
        }
        Object result = proceedingJoinPoint.proceed();
        log.debug("Cache not found, loading from database");
        redisCacheClient.incrementCacheMissInAsync(cacheMetaData);

        if (result instanceof ResponseEntity<?> responseEntity){
            Object responseBody = responseEntity.getBody();
            redisCacheClient.saveToRedisInAsync(cacheMetaData, responseBody);
            return prepareResponse(responseEntity, responseBody);
        } else {
            redisCacheClient.saveToRedisInAsync(cacheMetaData, result);
            return prepareResponse(result);
        }
    }

    private String getRequestHashFromHeader() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(attributes)) {
            HttpServletRequest request = attributes.getRequest();
            return request.getHeader(X_REQUEST_HASH_HEADER_KEY);
        }
        throw new IllegalStateException("Could not retrieve the current request attributes");
    }

    private static ResponseEntity<Object> prepareResponse(Object result) {
        return ResponseEntity
                .ok()
                .header(CONTENT_CACHED_RESPONSE_HEADER_KEY, CACHE_MISS_RESPONSE_HEADER_VALUE)
                .body(result);
    }

    private static ResponseEntity<Object> prepareResponse(ResponseEntity<?> responseEntity, Object responseBody) {
        return ResponseEntity
                .status(responseEntity.getStatusCode().value())
                .headers(responseEntity.getHeaders())
                .header(CONTENT_CACHED_RESPONSE_HEADER_KEY, CACHE_MISS_RESPONSE_HEADER_VALUE)
                .body(responseBody);
    }


}
