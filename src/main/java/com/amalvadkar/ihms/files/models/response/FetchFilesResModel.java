package com.amalvadkar.ihms.files.models.response;

import com.amalvadkar.ihms.common.models.dto.FetchFilesContentDTO;

import java.util.List;

public record FetchFilesResModel(List<FetchFilesContentDTO> content) {
}
