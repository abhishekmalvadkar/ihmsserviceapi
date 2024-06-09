package com.amalvadkar.ihms.feedback.controller.rest;


import com.amalvadkar.ihms.common.models.response.CustomResModel;
import com.amalvadkar.ihms.feedback.models.request.CheckFeedbackStatusReqModel;
import com.amalvadkar.ihms.feedback.services.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.amalvadkar.ihms.app.constants.AppConstants.FORM_DATA_JSON_DATA;
import static com.amalvadkar.ihms.app.constants.AppConstants.REQUEST_HEADER_USER_ID;

@RestController
@RequestMapping(value = "/api/ihms/feedback")
@RequiredArgsConstructor
public class FeedbackRestController {

    private static final String ENDPOINT_CREATE_FEEDBACK = "/create-feedback";
    private static final String ENDPOINT_CHECK_FEEDBACK_STATUS = "/check-feedback-status";

    private final FeedbackService feedbackService;

    @PostMapping(ENDPOINT_CREATE_FEEDBACK)
    public ResponseEntity<CustomResModel> createFeedback(@RequestParam(FORM_DATA_JSON_DATA) String createFeedbackReqJson,
                                                         @RequestParam(value = "files" , required = false) MultipartFile[] files,
                                                         @RequestHeader(REQUEST_HEADER_USER_ID) Long loggedInUserId) {
        return ResponseEntity.ok(feedbackService.createFeedback(createFeedbackReqJson , files , loggedInUserId));
    }

    @PostMapping(ENDPOINT_CHECK_FEEDBACK_STATUS)
    public ResponseEntity<CustomResModel> checkFeedBackStatus(@Valid @RequestBody CheckFeedbackStatusReqModel checkFeedBackStatusReqModel){
       return ResponseEntity.ok(this.feedbackService.checkFeedBackStatus(checkFeedBackStatusReqModel));
    }

}
