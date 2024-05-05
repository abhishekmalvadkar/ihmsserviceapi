package com.amalvadkar.ihms.feedback.services;

import com.amalvadkar.ihms.common.entities.FeedBackEntity;
import com.amalvadkar.ihms.common.entities.UserEntity;
import com.amalvadkar.ihms.common.helpers.JsonHelper;
import com.amalvadkar.ihms.common.models.response.CustomResModel;
import com.amalvadkar.ihms.common.repositories.FeedBackRepository;
import com.amalvadkar.ihms.common.repositories.UserRepository;
import com.amalvadkar.ihms.feedback.models.request.CreateFeedbackReqModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.amalvadkar.ihms.common.utils.AppConstants.CREATED_SUCCESSFULLY_RESPONSE_MESSAGE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedbackService {

    private final FeedBackRepository feedBackRepo;
    private final JsonHelper jsonHelper;
    private final UserRepository userRepo;

    @Transactional
    public CustomResModel createFeedback(String createFeedbackReqJson, Long loggedInUserId) {
        CreateFeedbackReqModel createFeedbackReqModel = jsonHelper.jsonToObj(createFeedbackReqJson, CreateFeedbackReqModel.class);

        UserEntity userEntity = userRepo.getReferenceById(loggedInUserId);

        FeedBackEntity feedBackEntity = new FeedBackEntity();
        feedBackEntity.setTitle(createFeedbackReqModel.getFeedbackTitle());
        feedBackEntity.setDescription(createFeedbackReqModel.getFeedbackDescription());
        feedBackEntity.setCreatedBy(userEntity);
        FeedBackEntity savedFeedbackEntity = feedBackRepo.save(feedBackEntity);
        return CustomResModel.success(Map.of("feedbackId", savedFeedbackEntity.getId()), CREATED_SUCCESSFULLY_RESPONSE_MESSAGE);
    }
}
