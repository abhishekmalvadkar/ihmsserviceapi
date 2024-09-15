package com.amalvadkar.ihms.files.request;

import jakarta.validation.constraints.NotNull;

public record FetchFilesRequest(
        @NotNull(message = "recordId is required")
        Long recordId
) {}
