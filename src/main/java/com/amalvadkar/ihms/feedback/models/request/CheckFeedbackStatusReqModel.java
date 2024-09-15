package com.amalvadkar.ihms.feedback.models.request;

import jakarta.validation.constraints.NotNull;

public record CheckFeedbackStatusReqModel(
        @NotNull(message = "feedbackId is required")
        Long feedbackId
) {}
