package com.amalvadkar.ihms.files.services;

import com.amalvadkar.ihms.common.entities.FileMetadataEntity;
import com.amalvadkar.ihms.common.exceptions.ResourceNotFoundException;
import com.amalvadkar.ihms.common.models.dto.FetchFilesContentDTO;
import com.amalvadkar.ihms.common.models.response.CustomResModel;
import com.amalvadkar.ihms.common.repositories.FileMetadataRepository;
import com.amalvadkar.ihms.files.helpers.DataBucketHelper;
import com.amalvadkar.ihms.files.models.response.FetchFilesResModel;
import com.amalvadkar.ihms.files.request.ViewFileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.amalvadkar.ihms.app.constants.AppConstants.CREATED_SUCCESSFULLY_RESPONSE_MESSAGE;
import static com.amalvadkar.ihms.app.constants.AppConstants.FETCHED_SUCCESSFULLY_RESPONSE_MESSAGE;
import static com.amalvadkar.ihms.app.constants.AppConstants.NO_FILES_MSG;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FilesService {

    private final DataBucketHelper dataBucketHelper;
    private final FileMetadataRepository fileMetadataRepo;

    public CustomResModel viewFile(ViewFileRequest viewFileRequest) {
        FileMetadataEntity fileMetadataEntity = fileMetadataRepo.findByIdAndDeleteFlagIsFalse(viewFileRequest.fileId())
                .orElseThrow(() -> new ResourceNotFoundException("File meta data not found"));
        String signedUrl = dataBucketHelper.signedUrl(fileMetadataEntity.getPath(), fileMetadataEntity.getFileName());
        return CustomResModel.success(signedUrl, CREATED_SUCCESSFULLY_RESPONSE_MESSAGE);
    }

    public CustomResModel fetchFiles(String recordId) {
        List<FetchFilesContentDTO> fetchFilesContentDTOList = fileMetadataRepo
                .findFilesMetadataByRecordId(recordId);
        FetchFilesResModel fetchFilesResModel = new FetchFilesResModel(fetchFilesContentDTOList);
        if (isEmpty(fetchFilesContentDTOList)) {
            return CustomResModel.success(fetchFilesResModel, NO_FILES_MSG);
        }
        return CustomResModel.success(fetchFilesResModel, FETCHED_SUCCESSFULLY_RESPONSE_MESSAGE);
    }

}
