package com.amalvadkar.ihms.holiday.services;

import com.amalvadkar.ihms.ApplicationProperties;
import com.amalvadkar.ihms.common.models.response.CustomResModel;
import com.amalvadkar.ihms.common.models.response.KeyValueResModel;
import com.amalvadkar.ihms.common.repositories.CountryRepository;
import com.amalvadkar.ihms.common.repositories.HolidayOverviewRepository;
import com.amalvadkar.ihms.holiday.mapper.HolidayOverviewMapper;
import com.amalvadkar.ihms.holiday.models.request.FetchHolidayOverviewReqModel;
import com.amalvadkar.ihms.holiday.models.response.HolidayOverviewMetadataResModel;
import com.amalvadkar.ihms.holiday.models.response.HolidayOverviewResModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.amalvadkar.ihms.common.utils.AppConstants.COUNTRY_LIST;
import static com.amalvadkar.ihms.common.utils.AppConstants.FETCHED_SUCCESSFULLY_RESPONSE_MESSAGE;
import static com.amalvadkar.ihms.common.utils.AppConstants.HOLIDAY_STATUS_LIST;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class HolidayOverviewService {

    private final ApplicationProperties appProps;
    private final CountryRepository countryRepo;
    private final HolidayOverviewRepository holidayOverviewRepo;
    private final HolidayOverviewMapper holidayOverviewMapper;

    public CustomResModel fetchHolidayOverviewMetadata() {
        var holidayOverviewMetadataResModel = new HolidayOverviewMetadataResModel(prepareMetadataMap());
        return CustomResModel.success(holidayOverviewMetadataResModel, FETCHED_SUCCESSFULLY_RESPONSE_MESSAGE);
    }

    private Map<String, Object> prepareMetadataMap() {
        List<KeyValueResModel> countryList = countryRepo.findCountryNamesWithIds();
        return Map.of(
                COUNTRY_LIST, countryList,
                HOLIDAY_STATUS_LIST, appProps.holidayStatusList()
        );
    }

    public CustomResModel fetchHolidayOverview(FetchHolidayOverviewReqModel fetchHolidayOverviewReqModel) {
        Long countryId = prepareCountryIdBasedOnRequest(fetchHolidayOverviewReqModel);
        var holidayOverviewEntities = holidayOverviewRepo.findHolidaysBasedOnCountryId(countryId);
        var holidayOverviewContentResModelList = holidayOverviewMapper.toResModelList(holidayOverviewEntities);
        return CustomResModel.success(new HolidayOverviewResModel(holidayOverviewContentResModelList), FETCHED_SUCCESSFULLY_RESPONSE_MESSAGE);
    }



    private Long prepareCountryIdBasedOnRequest(FetchHolidayOverviewReqModel fetchHolidayOverviewReqModel) {
        Long countryId = fetchHolidayOverviewReqModel.countryId();
        return isNull(countryId) ? countryRepo.findFirstCountryId() : countryId;
    }
}
