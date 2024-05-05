package com.amalvadkar.ihms.feedback.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFeedbackReqModel {

    private String feedbackTitle;
    private String feedbackDescription;
}
