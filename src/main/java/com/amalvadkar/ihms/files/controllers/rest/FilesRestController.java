package com.amalvadkar.ihms.files.controllers.rest;

import com.amalvadkar.ihms.common.models.response.CustomResModel;
import com.amalvadkar.ihms.files.request.FetchFilesRequest;
import com.amalvadkar.ihms.files.request.ViewFileRequest;
import com.amalvadkar.ihms.files.services.FilesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/ihms/files")
@RequiredArgsConstructor
public class FilesRestController {

    private static final String ENDPOINT_VIEW_FILE = "/view-file";
    public static final String ENDPOINT_FETCH_FILES = "/fetch-files";

    private final FilesService filesService;

    @PostMapping(ENDPOINT_VIEW_FILE)
    public ResponseEntity<CustomResModel> viewFile(@Valid @RequestBody ViewFileRequest viewFileRequest) {
        return ResponseEntity.ok(filesService.viewFile(viewFileRequest));
    }

    @PostMapping(ENDPOINT_FETCH_FILES)
    public ResponseEntity<CustomResModel> fetchFiles(@Valid @RequestBody FetchFilesRequest fetchFilesRequest){
        return ResponseEntity.ok(filesService.fetchFiles(fetchFilesRequest.recordId()));
    }


}
