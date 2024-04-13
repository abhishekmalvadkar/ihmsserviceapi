package com.amalvadkar.ihms.policy.models.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PolicyOverviewResModel {

    private Long policyCategoryId;
    private String policyCategoryName;

    List<PolicyDocumentResModel> policyDocuments;

}
