package com.amalvadkar.ihms.holiday.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum HolidayStatusCodeEnum {
    UPC("Upcoming"),
    HG("Has Gone");

    private final String title;
}
