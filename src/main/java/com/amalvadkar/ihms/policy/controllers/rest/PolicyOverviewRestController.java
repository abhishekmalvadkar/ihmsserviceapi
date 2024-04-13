package com.amalvadkar.ihms.policy.controllers.rest;

import com.amalvadkar.ihms.common.models.response.CustomResModel;
import com.amalvadkar.ihms.common.utils.AppConstants;
import com.amalvadkar.ihms.policy.services.PolicyOverviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/policy")
@RequiredArgsConstructor
public class PolicyOverviewRestController {
    private static final String ENDPOINT_FETCH_POLICY_OVERVIEW = "/fetchpolicyoverview";

    private final PolicyOverviewService policyOverviewService;

    @PostMapping(ENDPOINT_FETCH_POLICY_OVERVIEW)
    public ResponseEntity<CustomResModel> fetchPolicyOverview(
            @RequestHeader(AppConstants.REQUEST_HEADER_USER_ID) Long loggedInUserId,
            @RequestHeader(AppConstants.REQUEST_HEADER_ROLE_ID) Long roleId){
        return ResponseEntity.ok(policyOverviewService.fetchPolicyOverview());
    }

}
