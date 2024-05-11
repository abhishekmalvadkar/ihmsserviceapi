package com.amalvadkar.ihms.common.models.request;

import jakarta.validation.constraints.NotEmpty;

public record FetchFilesRequest(
        @NotEmpty(message = "recordId is required")
        String recordId
) {}
