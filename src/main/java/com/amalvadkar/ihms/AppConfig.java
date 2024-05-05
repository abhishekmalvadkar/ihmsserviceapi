package com.amalvadkar.ihms;

import com.amalvadkar.ihms.common.exceptions.ResourceNotFoundException;
import com.amalvadkar.ihms.holiday.enums.HolidayStatusCodeEnum;
import com.amalvadkar.ihms.holiday.models.dto.HolidayStatusDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.nio.charset.StandardCharsets;

import static com.amalvadkar.ihms.common.utils.AppConstants.CODE;
import static com.amalvadkar.ihms.common.utils.AppConstants.HOLIDAY_STATUS;
import static com.amalvadkar.ihms.holiday.enums.HolidayStatusCodeEnum.HG;
import static com.amalvadkar.ihms.holiday.enums.HolidayStatusCodeEnum.UPC;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final ApplicationProperties appProps;

    @Bean(name = "hasGoneHolidayStatus")
    public HolidayStatusDTO hasGoneHolidayStatus(){
        return appProps.holidayStatusList()
                .stream()
                .filter(holidayStatusDTO -> holidayStatusDTO.code() == HG)
                .findFirst()
                .orElseThrow(() -> ResourceNotFoundException.from(HOLIDAY_STATUS, CODE, HG.name()));
    }

    @Bean(name = "upcomingHolidayStatus")
    public HolidayStatusDTO upcomingHolidayStatus(){
        return appProps.holidayStatusList()
                .stream()
                .filter(holidayStatusDTO -> holidayStatusDTO.code() == HolidayStatusCodeEnum.UPC)
                .findFirst()
                .orElseThrow(() -> ResourceNotFoundException.from(HOLIDAY_STATUS, CODE, UPC.name()));
    }

    @Bean
    public SpringTemplateEngine springTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(htmlTemplateResolver());
        return templateEngine;
    }

    @Bean
    public SpringResourceTemplateResolver htmlTemplateResolver(){
        SpringResourceTemplateResolver emailTemplateResolver = new SpringResourceTemplateResolver();
        emailTemplateResolver.setPrefix("classpath:/email-templates/");
        emailTemplateResolver.setSuffix(".html");
        emailTemplateResolver.setTemplateMode(TemplateMode.HTML);
        emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        return emailTemplateResolver;
    }

}
