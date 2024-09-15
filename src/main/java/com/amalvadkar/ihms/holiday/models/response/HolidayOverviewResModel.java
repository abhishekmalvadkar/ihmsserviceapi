package com.amalvadkar.ihms.holiday.models.response;

import com.amalvadkar.ihms.app.models.response.HeaderResModel;

import java.util.List;

public record HolidayOverviewResModel(
        List<HeaderResModel> headers,
        List<HolidayOverviewContentResModel> content
) {
}
