package com.amalvadkar.ihms.common.models.request;

import jakarta.validation.constraints.NotEmpty;

public record ViewFileRequest(

        @NotEmpty(message = "fileId is required")
        String fileId
) {
}
