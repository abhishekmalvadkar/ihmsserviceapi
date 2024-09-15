package com.amalvadkar.ihms.files.request;

import jakarta.validation.constraints.NotNull;

public record ViewFileRequest(

        @NotNull(message = "fileId is required")
        Long fileId
) {
}
