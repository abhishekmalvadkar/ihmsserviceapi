package com.amalvadkar.ihms.policy.services;

import com.amalvadkar.ihms.common.entities.PolicyCategoryEntity;
import com.amalvadkar.ihms.common.entities.PolicyDocumentEntity;
import com.amalvadkar.ihms.common.models.response.CustomResModel;
import com.amalvadkar.ihms.common.repositories.PolicyCategoryRepository;
import com.amalvadkar.ihms.common.repositories.PolicyDocumentRepository;
import com.amalvadkar.ihms.common.utils.AppConstants;
import com.amalvadkar.ihms.policy.models.response.PolicyDocumentResModel;
import com.amalvadkar.ihms.policy.models.response.PolicyOverviewResModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PolicyOverviewService {

    private final PolicyCategoryRepository policyCategoryRepo;
    private final PolicyDocumentRepository policyDocumentRepo;

    public CustomResModel fetchPolicyOverview() {
        List<PolicyOverviewResModel> policyCategoryResModelList = preparePolicyOverviewResModelList();
        return CustomResModel.builder()
                .data(policyCategoryResModelList)
                .status(true)
                .message(AppConstants.FETCHED_SUCCESSFULLY_RESPONSE_MESSAGE)
                .code(HttpStatus.OK.value())
                .build();
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
}
