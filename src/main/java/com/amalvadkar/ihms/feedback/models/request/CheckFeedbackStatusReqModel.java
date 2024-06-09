package com.amalvadkar.ihms.feedback.models.request;

import jakarta.validation.constraints.NotEmpty;

public record CheckFeedbackStatusReqModel(
        @NotEmpty(message = "feedbackId is required")
        String feedbackId
) {}
