package com.amalvadkar.ihms.feedback.controller.rest;


import com.amalvadkar.ihms.common.models.response.CustomResModel;
import com.amalvadkar.ihms.feedback.services.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/ihms/feedback")
@RequiredArgsConstructor
public class FeedBackRestController {

    private static final String ENDPOINT_CREATE_FEEDBACK = "/create-feedback";

    private final FeedbackService feedbackService;

    @PostMapping(ENDPOINT_CREATE_FEEDBACK)
    public ResponseEntity<CustomResModel> createFeedback(@RequestParam("data") String createFeedbackReqJson,
                                                         @RequestHeader("userid") Long loggedInUserId) {
        return ResponseEntity.ok(feedbackService.createFeedback(createFeedbackReqJson, loggedInUserId));
    }

}
