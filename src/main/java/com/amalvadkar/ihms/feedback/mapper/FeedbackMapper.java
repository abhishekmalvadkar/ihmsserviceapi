package com.amalvadkar.ihms.feedback.mapper;

import com.amalvadkar.ihms.common.entities.FeedBackEntity;
import com.amalvadkar.ihms.feedback.models.response.FeedbackStatusResponse;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
public class FeedbackMapper {

    public FeedbackStatusResponse toFeedBackStatusModel(FeedBackEntity feedBackEntity) {
        FeedbackStatusResponse feedBackStatusResponse = new FeedbackStatusResponse();
        feedBackStatusResponse.setFeedbackStatus(feedBackEntity.getStatus());
        feedBackStatusResponse.setFeedbackId(feedBackEntity.getId());
        feedBackStatusResponse.setFeedbackTitle(feedBackEntity.getTitle());
        feedBackStatusResponse.setFeedbackDescription(feedBackEntity.getDescription());
        feedBackStatusResponse.setCreatedOn(feedBackEntity.getCreatedOn());
        feedBackStatusResponse.setUpdatedOn(feedBackEntity.getUpdatedOn());
        feedBackStatusResponse.setUpdatedBy(feedBackEntity.getUpdatedBy().getName());
        feedBackStatusResponse.setReviewComment(feedBackEntity.getReviewComment());
        feedBackStatusResponse.setCreatedBy(feedBackEntity.getCreatedBy().getName());
        if (nonNull(feedBackEntity.getReviewedBy())) {
            feedBackStatusResponse.setReviewedBy(feedBackEntity.getReviewedBy().getName());
        }
        feedBackStatusResponse.setReviewedOn(feedBackEntity.getReviewedOn());
        return feedBackStatusResponse;
    }
}
