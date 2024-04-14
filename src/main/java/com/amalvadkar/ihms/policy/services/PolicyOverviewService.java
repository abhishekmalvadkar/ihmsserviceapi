package com.amalvadkar.ihms.policy.services;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.amalvadkar.ihms.common.utils.AppConstants.CREATED_SUCCESSFULLY_RESPONSE_MESSAGE;
import static com.amalvadkar.ihms.common.utils.AppConstants.FETCHED_SUCCESSFULLY_RESPONSE_MESSAGE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PolicyOverviewService {

    private final PolicyCategoryRepository policyCategoryRepo;
    private final PolicyDocumentRepository policyDocumentRepo;
    private final PolicyDocumentViewCountHelper policyDocumentViewCountHelper;

    public CustomResModel fetchPolicyOverview() {
        List<PolicyOverviewResModel> policyOverviewResModelList = preparePolicyOverviewResModelList();
        return CustomResModel.success(policyOverviewResModelList, FETCHED_SUCCESSFULLY_RESPONSE_MESSAGE);
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
        Long policyDocumentId = viewPolicyDocumentRequest.policyDocumentId();
        PolicyDocumentEntity policyDocumentEntity = policyDocumentRepo.findByIdOrThrow(policyDocumentId);
        policyDocumentViewCountHelper.increaseViewCountInAsync(policyDocumentId);
        return CustomResModel.success(policyDocumentEntity.getPath() , CREATED_SUCCESSFULLY_RESPONSE_MESSAGE);
    }
}
