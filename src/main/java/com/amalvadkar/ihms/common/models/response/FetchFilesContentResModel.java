package com.amalvadkar.ihms.common.models.response;

import java.time.Instant;

public record FetchFilesContentResModel(String fileId, String fileName, String uploadedBy, Instant uploadedOn) {
}
