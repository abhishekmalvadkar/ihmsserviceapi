package com.amalvadkar.ihms.policy.controllers.rest;

import com.amalvadkar.ihms.common.models.response.CustomResModel;
import com.amalvadkar.ihms.policy.models.request.ViewPolicyDocumentRequest;
import com.amalvadkar.ihms.policy.services.PolicyOverviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/ihms/policy")
@RequiredArgsConstructor
public class PolicyOverviewRestController {
    private static final String ENDPOINT_FETCH_POLICY_OVERVIEW = "/fetchpolicyoverview";
    private static final String ENDPOINT_VIEW_POLICY_DOCUMENT = "/viewpolicydocument";

    private final PolicyOverviewService policyOverviewService;

    @PostMapping(ENDPOINT_FETCH_POLICY_OVERVIEW)
    public ResponseEntity<CustomResModel> fetchPolicyOverview(){
        return ResponseEntity.ok(policyOverviewService.fetchPolicyOverview());
    }

    @PostMapping(ENDPOINT_VIEW_POLICY_DOCUMENT)
    public ResponseEntity<CustomResModel> viewPolicyDocument(@Valid @RequestBody ViewPolicyDocumentRequest viewPolicyDocumentRequest){
        return ResponseEntity.ok(policyOverviewService.viewPolicyDocument(viewPolicyDocumentRequest));
    }

}
