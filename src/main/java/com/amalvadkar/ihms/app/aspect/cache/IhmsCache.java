package com.amalvadkar.ihms.app.aspect.cache;

import com.amalvadkar.ihms.app.enums.CacheKeyPrefixEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IhmsCache {

    CacheKeyPrefixEnum cacheKeyPrefix() default CacheKeyPrefixEnum.NONE;
    String expiryTimeInSec() default "60";

}
