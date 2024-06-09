package com.amalvadkar.ihms.feedback.models.response;

import com.amalvadkar.ihms.common.enums.FeedbackStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class FeedbackStatusResponse {

    private String feedbackId;
    private String feedbackTitle;

    private String feedbackDescription;

    private Instant createdOn;

    private String createdBy;

    private Instant updatedOn;

    private String updatedBy;

    private String reviewedBy;

    private Instant reviewedOn;

    private String reviewComment;

    private FeedbackStatusEnum feedbackStatus;

}