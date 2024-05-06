package com.amalvadkar.ihms.feedback.services;

import com.amalvadkar.ihms.common.entities.FeedBackEntity;
import com.amalvadkar.ihms.common.entities.UserEntity;
import com.amalvadkar.ihms.common.helpers.JsonHelper;
import com.amalvadkar.ihms.common.models.response.CustomResModel;
import com.amalvadkar.ihms.common.repositories.FeedBackRepository;
import com.amalvadkar.ihms.common.repositories.UserRepository;
import com.amalvadkar.ihms.common.utils.HtmlSanitizer;
import com.amalvadkar.ihms.email.dto.MailDTO;
import com.amalvadkar.ihms.email.sender.EmailSender;
import com.amalvadkar.ihms.feedback.models.request.CreateFeedbackReqModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.amalvadkar.ihms.common.utils.AppConstants.CREATED_SUCCESSFULLY_RESPONSE_MESSAGE;
import static com.amalvadkar.ihms.common.utils.AppConstants.CREATE_FEEDBACK_SUCCESS_EMAIL_TEMPLATE_NAME;
import static com.amalvadkar.ihms.common.utils.AppConstants.EMAIL_SUBJECT_FEEDBACK_ADDED_SUCCESSFULLY;
import static com.amalvadkar.ihms.common.utils.AppConstants.FEEDBACK_DESCRIPTION;
import static com.amalvadkar.ihms.common.utils.AppConstants.FEEDBACK_ID;
import static com.amalvadkar.ihms.common.utils.AppConstants.FEEDBACK_STATUS;
import static com.amalvadkar.ihms.common.utils.AppConstants.FEEDBACK_TITLE;
import static com.amalvadkar.ihms.common.utils.AppConstants.USERNAME;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedbackService {

    private final FeedBackRepository feedBackRepo;
    private final JsonHelper jsonHelper;
    private final UserRepository userRepo;
    private final EmailSender emailSender;

    @Transactional
    public CustomResModel createFeedback(String createFeedbackReqJson, Long loggedInUserId) {
        UserEntity userEntity = userRepo.getReferenceById(loggedInUserId);
        FeedBackEntity savedFeedbackEntity = saveFeedback(createFeedbackReqJson, userEntity);
        sendFeedbackAddedEmailWithDetails(userEntity, savedFeedbackEntity);
        return CustomResModel.success(Map.of(FEEDBACK_ID, savedFeedbackEntity.getId()), CREATED_SUCCESSFULLY_RESPONSE_MESSAGE);
    }

    private FeedBackEntity saveFeedback(String createFeedbackReqJson, UserEntity userEntity) {
        CreateFeedbackReqModel createFeedbackReqModel = jsonHelper.jsonToObj(createFeedbackReqJson, CreateFeedbackReqModel.class);
        String sanitizedDescription = HtmlSanitizer.sanitize(createFeedbackReqModel.feedbackDescription());
        FeedBackEntity feedBackEntity = new FeedBackEntity();
        feedBackEntity.setTitle(createFeedbackReqModel.feedbackTitle());
        feedBackEntity.setDescription(sanitizedDescription);
        feedBackEntity.setCreatedBy(userEntity);
        return feedBackRepo.save(feedBackEntity);
    }

    private void sendFeedbackAddedEmailWithDetails(UserEntity userEntity, FeedBackEntity feedBackEntity) {
        Map<String, Object> templateVariableNameToValueMap = Map.of(
                USERNAME, userEntity.getName(),
                FEEDBACK_ID, feedBackEntity.getId(),
                FEEDBACK_TITLE, feedBackEntity.getTitle(),
                FEEDBACK_DESCRIPTION, feedBackEntity.getDescription(),
                FEEDBACK_STATUS, feedBackEntity.getStatus()
        );
        MailDTO mailDTO = new MailDTO(EMAIL_SUBJECT_FEEDBACK_ADDED_SUCCESSFULLY,
                userEntity.getEmail(), templateVariableNameToValueMap, CREATE_FEEDBACK_SUCCESS_EMAIL_TEMPLATE_NAME);
        emailSender.sendInAsync(mailDTO);
    }
}
