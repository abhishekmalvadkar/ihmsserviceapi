package com.amalvadkar.ihms.holiday.controllers.rest;

import com.amalvadkar.ihms.app.aspect.cache.IhmsCache;
import com.amalvadkar.ihms.common.models.response.CustomResModel;
import com.amalvadkar.ihms.holiday.models.request.FetchHolidayOverviewReqModel;
import com.amalvadkar.ihms.holiday.services.HolidayOverviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.amalvadkar.ihms.app.enums.CacheKeyPrefixEnum.FETCH_HOLIDAY_OVERVIEW;

@RestController
@RequestMapping(value = "/api/ihms/holiday")
@RequiredArgsConstructor
public class HolidayOverviewRestController {
    private static final String ENDPOINT_FETCH_HOLIDAY_OVERVIEW_METADATA = "/fetch-holiday-overview-metadata";
    private static final String ENDPOINT_FETCH_HOLIDAY_OVERVIEW = "/fetch-holiday-overview";

    private final HolidayOverviewService holidayOverviewService;

    @PostMapping(ENDPOINT_FETCH_HOLIDAY_OVERVIEW_METADATA)
    public ResponseEntity<CustomResModel> fetchHolidayOverviewMetadata(){
        return ResponseEntity.ok(holidayOverviewService.fetchHolidayOverviewMetadata());
    }

    @PostMapping(ENDPOINT_FETCH_HOLIDAY_OVERVIEW)
    @IhmsCache(expiryTimeInSec = "86400", cacheKeyPrefix = FETCH_HOLIDAY_OVERVIEW)
    public ResponseEntity<CustomResModel> fetchHolidayOverview(@RequestBody FetchHolidayOverviewReqModel fetchHolidayOverviewReqModel,
                                                               @RequestHeader("roleId") Long roleId){
        return ResponseEntity.ok(holidayOverviewService.fetchHolidayOverview(fetchHolidayOverviewReqModel, roleId));
    }

}
