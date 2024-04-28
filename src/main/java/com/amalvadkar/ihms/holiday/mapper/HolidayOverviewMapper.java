package com.amalvadkar.ihms.holiday.mapper;

import com.amalvadkar.ihms.ApplicationProperties;
import com.amalvadkar.ihms.common.entities.HolidayOverviewEntity;
import com.amalvadkar.ihms.holiday.models.dto.HolidayStatusDTO;
import com.amalvadkar.ihms.holiday.models.response.HolidayOverviewContentResModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HolidayOverviewMapper {

    private final ApplicationProperties appProps;

    @Qualifier("hasGoneHolidayStatus")
    private final HolidayStatusDTO hasGoneHolidayStatus;

    @Qualifier("upcomingHolidayStatus")
    private final HolidayStatusDTO upcomingHolidayStatus;

    public List<HolidayOverviewContentResModel> toResModelList(List<HolidayOverviewEntity> holidayOverviewEntities) {
        return holidayOverviewEntities
                .stream()
                .map(this::toResModel)
                .toList();
    }

    private HolidayOverviewContentResModel toResModel(HolidayOverviewEntity entity) {
        HolidayStatusDTO holidayStatusDTO = prepareHolidayStatus(entity);
        return HolidayOverviewContentResModel.builder()
                .holidayId(entity.getId())
                .holidayDate(entity.getHolidayDate())
                .holidayTitle(entity.getTitle())
                .isOptional(entity.getIsOptional())
                .country(entity.getCountryEntity().getName())
                .holidayStatus(holidayStatusDTO.title())
                .holidayStatusColor(holidayStatusDTO.colorCode())
                .build();
    }

    private HolidayStatusDTO prepareHolidayStatus(HolidayOverviewEntity entity) {
        return hasHolidayGone(entity) ? hasGoneHolidayStatus : upcomingHolidayStatus;
    }

    private boolean hasHolidayGone(HolidayOverviewEntity entity) {
        return entity.getHolidayDate().isBefore(LocalDate.now());
    }

}
