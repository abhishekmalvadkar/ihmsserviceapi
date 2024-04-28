package com.amalvadkar.ihms.holiday.models.dto;

import com.amalvadkar.ihms.holiday.enums.HolidayStatusCodeEnum;

public record HolidayStatusDTO(String title, String colorCode, HolidayStatusCodeEnum code) {
}
