package com.amalvadkar.ihms.app.client;

import com.amalvadkar.ihms.app.models.dto.CacheMetaData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisCacheClient {

    private static final String CACHE_HIT_KEY = "cache_hit";
    private static final String CACHE_MISS_KEY = "cache_miss";
    private static final String CACHE_KEY_SEGMENT_SEPARATOR = ":";
    private static final String RESPONSE_KEY = "response";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private HashOperations<String, String, String> hashOperations;

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    public String getCachedResponse(CacheMetaData cacheMetaData) {
        return hashOperations.get(prepareCacheKey(cacheMetaData), RESPONSE_KEY);
    }

    @Async
    public void saveToRedisInAsync(CacheMetaData cacheMetaData, Object result) {
        try {
            String jsonResponse = objectMapper.writeValueAsString(result);
            String cacheKey = prepareCacheKey(cacheMetaData);
            hashOperations.put(cacheKey, RESPONSE_KEY, jsonResponse);
            redisTemplate.expire(cacheKey, Long.parseLong(cacheMetaData.expiryTimeInSec()), TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            log.error("Exception occurred while saving response in redis cache", e);
        }
    }

    private static String prepareCacheKey(CacheMetaData cacheMetaData) {
        if (isNotEmpty(cacheMetaData.keyPrefix())){
            return cacheMetaData.keyPrefix() + CACHE_KEY_SEGMENT_SEPARATOR + cacheMetaData.key();
        } else {
            return cacheMetaData.key();
        }
    }

    @Async
    public void incrementCacheHitInAsync(CacheMetaData cacheMetaData) {
        hashOperations.increment(prepareCacheKey(cacheMetaData), CACHE_HIT_KEY, 1);
    }

    public void incrementCacheMissInAsync(CacheMetaData cacheMetaData) {
        hashOperations.increment(prepareCacheKey(cacheMetaData), CACHE_MISS_KEY, 1);
    }

    public Map<String, String> getCacheStats(CacheMetaData cacheMetaData) {
        return hashOperations.entries(prepareCacheKey(cacheMetaData));
    }

}
