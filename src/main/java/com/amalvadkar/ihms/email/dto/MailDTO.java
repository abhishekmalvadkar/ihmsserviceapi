package com.amalvadkar.ihms.email.dto;

import java.util.Map;

public record MailDTO(
        String subject,

        String toMail,

        Map<String, Object> props,

        String templateFileName
) {
}
