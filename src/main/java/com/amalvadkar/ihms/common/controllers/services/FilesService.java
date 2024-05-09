package com.amalvadkar.ihms.common.controllers.services;

import com.amalvadkar.ihms.common.entities.FileMetadataEntity;
import com.amalvadkar.ihms.common.exceptions.ResourceNotFoundException;
import com.amalvadkar.ihms.common.helpers.DataBucketHelper;
import com.amalvadkar.ihms.common.models.request.ViewFileRequest;
import com.amalvadkar.ihms.common.models.response.CustomResModel;
import com.amalvadkar.ihms.common.repositories.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.amalvadkar.ihms.common.utils.AppConstants.CREATED_SUCCESSFULLY_RESPONSE_MESSAGE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FilesService {

    private final DataBucketHelper dataBucketHelper;
    private final FileMetadataRepository fileMetadataRepos;
    public CustomResModel viewFile(ViewFileRequest viewFileRequest) {
        FileMetadataEntity fileMetadataEntity = fileMetadataRepos.findByIdAndDeleteFlagIsFalse(viewFileRequest.fileId())
                .orElseThrow(() -> new ResourceNotFoundException("File meta data not found"));
        String signedUrl = dataBucketHelper.signedUrl(fileMetadataEntity.getPath(), fileMetadataEntity.getFileName());
        return CustomResModel.success(signedUrl, CREATED_SUCCESSFULLY_RESPONSE_MESSAGE);
    }
}
