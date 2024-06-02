package com.amalvadkar.ihms.holiday.models.response;

import com.amalvadkar.ihms.common.enums.OptionalStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Data
@Builder
public class HolidayOverviewContentResModel {

    private Long holidayId;

    @JsonFormat(shape = Shape.STRING, pattern = "dd MMM yyyy")
    private LocalDate holidayDate;

    private String holidayTitle;
    private OptionalStatusEnum isOptional;
    private String country;
    private String holidayStatusColor;
    private String holidayStatus;

    public String getDay(){
        DayOfWeek dayOfWeek = holidayDate.getDayOfWeek();
        return StringUtils.capitalize(dayOfWeek.name().toLowerCase());
    }

}
