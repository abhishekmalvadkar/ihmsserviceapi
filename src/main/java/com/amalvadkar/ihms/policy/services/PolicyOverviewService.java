package com.amalvadkar.ihms.policy.services;

import com.amalvadkar.ihms.app.constants.AppConstants;
import com.amalvadkar.ihms.common.entities.PolicyCategoryEntity;
import com.amalvadkar.ihms.common.entities.PolicyDocumentEntity;
import com.amalvadkar.ihms.common.models.response.CustomResModel;
import com.amalvadkar.ihms.common.repositories.PolicyCategoryRepository;
import com.amalvadkar.ihms.common.repositories.PolicyDocumentRepository;
import com.amalvadkar.ihms.policy.helpers.PolicyDocumentViewCountHelper;
import com.amalvadkar.ihms.policy.models.request.ViewPolicyDocumentRequest;
import com.amalvadkar.ihms.policy.models.response.PolicyDocumentResModel;
import com.amalvadkar.ihms.policy.models.response.PolicyOverviewResModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PolicyOverviewService {

    private final PolicyCategoryRepository policyCategoryRepo;
    private final PolicyDocumentRepository policyDocumentRepo;
    private final PolicyDocumentViewCountHelper policyDocumentViewCountHelper;

    public CustomResModel fetchPolicyOverview() {
        List<PolicyOverviewResModel> policyOverviewResModelList = preparePolicyOverviewResModelList();
        return CustomResModel.success(policyOverviewResModelList, AppConstants.FETCHED_SUCCESSFULLY_RESPONSE_MESSAGE);
    }

    private List<PolicyOverviewResModel> preparePolicyOverviewResModelList() {
        List<PolicyCategoryEntity> policyCategoryEntities = policyCategoryRepo.findAllPolicyCategories();
        return policyCategoryEntities.stream()
                .map(this::preparePolicyOverviewResModel)
                .toList();
    }

    private PolicyOverviewResModel preparePolicyOverviewResModel(PolicyCategoryEntity policyCategoryEntity) {
        PolicyOverviewResModel policyOverviewResModel = new PolicyOverviewResModel();
        policyOverviewResModel.setPolicyCategoryId(policyCategoryEntity.getId());
        policyOverviewResModel.setPolicyCategoryName(policyCategoryEntity.getName());
        List<PolicyDocumentResModel> policyDocumentResModelList = preparePolicyDocumentResModelList(policyCategoryEntity);
        policyOverviewResModel.setPolicyDocuments(policyDocumentResModelList);
        return policyOverviewResModel;
    }

    private List<PolicyDocumentResModel> preparePolicyDocumentResModelList(PolicyCategoryEntity policyCategoryEntity) {
        List<PolicyDocumentEntity> policyDocumentEntities =
                policyDocumentRepo.findDocumentsByPolicyCategoryId(policyCategoryEntity.getId());
        return policyDocumentEntities.stream()
                .map(this::preparePolicyDocumentResModel)
                .toList();
    }

    private PolicyDocumentResModel preparePolicyDocumentResModel(PolicyDocumentEntity policyDocumentEntity) {
        PolicyDocumentResModel policyDocumentResModel = new PolicyDocumentResModel();
        policyDocumentResModel.setPolicyDocumentId(policyDocumentEntity.getId());
        policyDocumentResModel.setPolicyDocumentTitle(policyDocumentEntity.titleWithViews());
        return policyDocumentResModel;
    }

    public CustomResModel viewPolicyDocument(ViewPolicyDocumentRequest viewPolicyDocumentRequest) {
        log.debug("<<<<<<<< viewPolicyDocument()");
        Long policyDocumentId = viewPolicyDocumentRequest.policyDocumentId();
        PolicyDocumentEntity policyDocumentEntity = policyDocumentRepo.findByIdOrThrow(policyDocumentId);
        policyDocumentViewCountHelper.increaseViewCountInAsync(policyDocumentId);
        log.debug("viewPolicyDocument() >>>>>>>");
        return CustomResModel.success(policyDocumentEntity.getPath() , AppConstants.CREATED_SUCCESSFULLY_RESPONSE_MESSAGE);
    }
}
