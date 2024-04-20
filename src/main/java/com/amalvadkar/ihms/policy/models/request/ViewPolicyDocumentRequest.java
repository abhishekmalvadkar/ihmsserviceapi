package com.amalvadkar.ihms.policy.models.request;


import jakarta.validation.constraints.NotNull;

public record ViewPolicyDocumentRequest(
        @NotNull(message = "policyDocumentId is required")
        Long policyDocumentId) {
}
