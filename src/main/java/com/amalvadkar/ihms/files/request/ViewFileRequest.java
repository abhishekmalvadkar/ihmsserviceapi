package com.amalvadkar.ihms.files.request;

import jakarta.validation.constraints.NotEmpty;

public record ViewFileRequest(

        @NotEmpty(message = "fileId is required")
        String fileId
) {
}
