package com.amalvadkar.ihms.feedback.services;

import com.amalvadkar.ihms.ApplicationProperties;
import com.amalvadkar.ihms.app.constants.AppConstants;
import com.amalvadkar.ihms.common.entities.FeedBackEntity;
import com.amalvadkar.ihms.common.entities.FileMetadataEntity;
import com.amalvadkar.ihms.common.entities.UserEntity;
import com.amalvadkar.ihms.common.enums.CategoryEnum;
import com.amalvadkar.ihms.common.helpers.JsonHelper;
import com.amalvadkar.ihms.common.models.response.CustomResModel;
import com.amalvadkar.ihms.common.repositories.FeedBackRepository;
import com.amalvadkar.ihms.common.repositories.FileMetadataRepository;
import com.amalvadkar.ihms.common.repositories.UserRepository;
import com.amalvadkar.ihms.common.utils.Sanitizer;
import com.amalvadkar.ihms.email.dto.MailDTO;
import com.amalvadkar.ihms.email.sender.EmailSender;
import com.amalvadkar.ihms.feedback.models.request.CreateFeedbackReqModel;
import com.amalvadkar.ihms.files.helpers.DataBucketHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.ArrayUtils.isEmpty;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FeedbackService {

    private final FeedBackRepository feedBackRepo;
    private final JsonHelper jsonHelper;
    private final UserRepository userRepo;
    private final EmailSender emailSender;
    private final ApplicationProperties appProps;
    private final DataBucketHelper dataBucketHelper;
    private final FileMetadataRepository fileMetadataRepo;

    @Transactional
    public CustomResModel createFeedback(String createFeedbackReqJson, MultipartFile[] files, Long loggedInUserId) {
        UserEntity userEntity = userRepo.getReferenceById(loggedInUserId);
        FeedBackEntity savedFeedbackEntity = saveFeedback(createFeedbackReqJson, userEntity);
        sendFeedbackAddedEmailWithDetails(userEntity, savedFeedbackEntity);
        processFiles(files, savedFeedbackEntity , userEntity);
        return CustomResModel.success(Map.of(AppConstants.FEEDBACK_ID, savedFeedbackEntity.getId()), AppConstants.CREATED_SUCCESSFULLY_RESPONSE_MESSAGE);
    }

    private void processFiles(MultipartFile[] files, FeedBackEntity savedFeedbackEntity, UserEntity userEntity) {
        if (isEmpty(files)) {
           log.info("file(s) not selected");
           return;
        }
        String directoryPath = appProps.feedbackImagesBasePath() + savedFeedbackEntity.getId();
        saveFilesMetadataToDB(files, savedFeedbackEntity, userEntity, directoryPath);
        uploadFilesToFcs(files, directoryPath);
    }

    private void uploadFilesToFcs(MultipartFile[] files, String directoryPath) {
        for (MultipartFile file : files) {
            String sanitizedFileName = Sanitizer.sanitizeFileName(file.getOriginalFilename());
            dataBucketHelper.upload(file, directoryPath, sanitizedFileName);
        }
    }

    private void saveFilesMetadataToDB(MultipartFile[] files, FeedBackEntity savedFeedbackEntity, UserEntity userEntity, String directoryPath) {
        List<FileMetadataEntity> fileMetadataEntityListToSave = new ArrayList<>();
        for (MultipartFile file : files) {
            String sanitizedFileName = Sanitizer.sanitizeFileName(file.getOriginalFilename());

            FileMetadataEntity fileMetadataEntity = new FileMetadataEntity();
            fileMetadataEntity.setCategoryEnum(CategoryEnum.FEEDBACK);
            fileMetadataEntity.setRecordId(savedFeedbackEntity.getId());
            fileMetadataEntity.setFileName(sanitizedFileName);
            fileMetadataEntity.setPath(directoryPath);
            fileMetadataEntity.setCreatedBy(userEntity);

            fileMetadataEntityListToSave.add(fileMetadataEntity);
        }
        fileMetadataRepo.saveAll(fileMetadataEntityListToSave);
    }

    private FeedBackEntity saveFeedback(String createFeedbackReqJson, UserEntity userEntity) {
        CreateFeedbackReqModel createFeedbackReqModel = jsonHelper.jsonToObj(createFeedbackReqJson, CreateFeedbackReqModel.class);
        String sanitizedDescription = Sanitizer.sanitizeHtml(createFeedbackReqModel.feedbackDescription());
        FeedBackEntity feedBackEntity = new FeedBackEntity();
        feedBackEntity.setTitle(createFeedbackReqModel.feedbackTitle());
        feedBackEntity.setDescription(sanitizedDescription);
        feedBackEntity.setCreatedBy(userEntity);
        return feedBackRepo.save(feedBackEntity);
    }

    private void sendFeedbackAddedEmailWithDetails(UserEntity userEntity, FeedBackEntity feedBackEntity) {
        Map<String, Object> templateVariableNameToValueMap = Map.of(
                AppConstants.USERNAME, userEntity.getName(),
                AppConstants.FEEDBACK_ID, feedBackEntity.getId(),
                AppConstants.FEEDBACK_TITLE, feedBackEntity.getTitle(),
                AppConstants.FEEDBACK_DESCRIPTION, feedBackEntity.getDescription(),
                AppConstants.FEEDBACK_STATUS, feedBackEntity.getStatus()
        );
        MailDTO mailDTO = new MailDTO(AppConstants.EMAIL_SUBJECT_FEEDBACK_ADDED_SUCCESSFULLY,
                userEntity.getEmail(), templateVariableNameToValueMap, AppConstants.CREATE_FEEDBACK_SUCCESS_EMAIL_TEMPLATE_NAME);
        emailSender.sendInAsync(mailDTO);
    }
}
