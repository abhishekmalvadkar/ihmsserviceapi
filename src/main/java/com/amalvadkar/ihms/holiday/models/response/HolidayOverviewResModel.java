package com.amalvadkar.ihms.holiday.models.response;

import java.util.List;

public record HolidayOverviewResModel(
        List<HolidayOverviewContentResModel> content
) {
}
