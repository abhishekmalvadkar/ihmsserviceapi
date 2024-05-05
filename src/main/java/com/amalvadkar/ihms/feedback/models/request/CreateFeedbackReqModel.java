package com.amalvadkar.ihms.feedback.models.request;

public record CreateFeedbackReqModel(
        String feedbackTitle,
        String feedbackDescription
) {
}
