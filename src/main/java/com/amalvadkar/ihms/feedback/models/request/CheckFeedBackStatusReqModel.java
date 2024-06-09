package com.amalvadkar.ihms.feedback.models.request;

import jakarta.validation.constraints.NotEmpty;

public record CheckFeedBackStatusReqModel(
        @NotEmpty(message = "feedbackId is required")
        String feedbackId
) {}
