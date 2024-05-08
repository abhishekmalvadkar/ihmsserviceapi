package com.amalvadkar.ihms;

import com.amalvadkar.ihms.holiday.models.dto.HolidayStatusDTO;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@ConfigurationProperties(prefix = "ihms")
@Validated
public record ApplicationProperties(
        @NotEmpty(message = "holidayStatusList property should not be null or empty")
        List<HolidayStatusDTO> holidayStatusList,
        @DefaultValue("false")
        boolean emailSendEnabled,
        @NotEmpty(message = "bucketName property should not be null or empty")
        String bucketName,
        @DefaultValue("7200")
        Long signedUrlExpiryTimeInSec,

        @DefaultValue("true")
        boolean blobStorageEnabled

) {
}
