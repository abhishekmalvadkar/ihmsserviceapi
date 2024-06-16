package com.amalvadkar.ihms.app.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CacheKeyPrefixEnum {

    NONE(""),

    FETCH_HOLIDAY_OVERVIEW("fetch-holiday-overview");

    private final String prefix;

}
