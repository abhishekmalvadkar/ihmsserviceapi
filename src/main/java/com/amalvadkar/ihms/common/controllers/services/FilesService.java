package com.amalvadkar.ihms.common.controllers.services;

import com.amalvadkar.ihms.common.entities.FileMetadataEntity;
import com.amalvadkar.ihms.common.exceptions.ResourceNotFoundException;
import com.amalvadkar.ihms.common.helpers.DataBucketHelper;
import com.amalvadkar.ihms.common.models.request.ViewFileRequest;
import com.amalvadkar.ihms.common.models.response.CustomResModel;
import com.amalvadkar.ihms.common.models.response.FetchFilesContentResModel;
import com.amalvadkar.ihms.common.models.response.FetchFilesResModel;
import com.amalvadkar.ihms.common.repositories.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.amalvadkar.ihms.common.utils.AppConstants.CREATED_SUCCESSFULLY_RESPONSE_MESSAGE;
import static com.amalvadkar.ihms.common.utils.AppConstants.FETCHED_SUCCESSFULLY_RESPONSE_MESSAGE;
import static com.amalvadkar.ihms.common.utils.AppConstants.NO_FILES_MSG;
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
        List<FetchFilesContentResModel> fetchFilesContentResModelList = fileMetadataRepo
                .findFilesMetadataByRecordId(recordId);
        FetchFilesResModel fetchFilesResModel = new FetchFilesResModel(fetchFilesContentResModelList);
        if (isEmpty(fetchFilesContentResModelList)) {
            return CustomResModel.success(fetchFilesResModel, NO_FILES_MSG);
        }
        return CustomResModel.success(fetchFilesResModel, FETCHED_SUCCESSFULLY_RESPONSE_MESSAGE);
    }

}
