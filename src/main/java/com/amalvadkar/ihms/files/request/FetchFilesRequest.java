package com.amalvadkar.ihms.files.request;

import jakarta.validation.constraints.NotEmpty;

public record FetchFilesRequest(
        @NotEmpty(message = "recordId is required")
        String recordId
) {}
