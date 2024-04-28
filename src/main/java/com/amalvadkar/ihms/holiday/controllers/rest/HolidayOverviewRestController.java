package com.amalvadkar.ihms.holiday.controllers.rest;

import com.amalvadkar.ihms.common.models.response.CustomResModel;
import com.amalvadkar.ihms.holiday.services.HolidayOverviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/ihms/holiday")
@RequiredArgsConstructor
public class HolidayOverviewRestController {
    private static final String ENDPOINT_FETCH_HOLIDAY_OVERVIEW_METADATA = "/fetch-holiday-overview-metadata";

    private final HolidayOverviewService holidayOverviewService;

    @PostMapping(ENDPOINT_FETCH_HOLIDAY_OVERVIEW_METADATA)
    public ResponseEntity<CustomResModel> fetchHolidayOverviewMetadata(){
        return ResponseEntity.ok(holidayOverviewService.fetchHolidayOverviewMetadata());
    }

}
