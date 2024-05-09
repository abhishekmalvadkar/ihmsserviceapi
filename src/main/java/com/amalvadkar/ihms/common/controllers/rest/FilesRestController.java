package com.amalvadkar.ihms.common.controllers.rest;

import com.amalvadkar.ihms.common.controllers.services.FilesService;
import com.amalvadkar.ihms.common.models.request.ViewFileRequest;
import com.amalvadkar.ihms.common.models.response.CustomResModel;
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

    private final FilesService filesService;

    @PostMapping(ENDPOINT_VIEW_FILE)
    public ResponseEntity<CustomResModel> viewFile(@Valid @RequestBody ViewFileRequest viewFileRequest) {
        return ResponseEntity.ok(filesService.viewFile(viewFileRequest));
    }

}
