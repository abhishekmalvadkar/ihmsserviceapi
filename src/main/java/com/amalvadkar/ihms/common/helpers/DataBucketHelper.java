package com.amalvadkar.ihms.common.helpers;

import com.amalvadkar.ihms.ApplicationProperties;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

import static com.amalvadkar.ihms.common.utils.AppConstants.SLASH;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.concurrent.TimeUnit.SECONDS;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataBucketHelper {
    private static final String FILE_UPLOADED_SUCCESSFULLY_TO_FCS_MSG = "File {} uploaded successfully to FCS on {} path";
    private static final String SIGNED_URL_GENERATED_SUCCESSFULLY_MSG = "Signed url generated successfully for {} file";
    public static final String FAILED_TO_GENERATE_SIGN_IN_URL_MSG = "Failed to generate sign in URL for file - {}";

    @Qualifier("fireBaseStorage")
    private final Storage storage;

    private final ApplicationProperties appProps;

    public void upload(MultipartFile file, String directoryPath, String fileName) {
        if (blobStorageIsDisabled()){
            log.warn("Can't upload because blog storage feature is disabled");
            return;
        }
        try {
            String filePath = directoryPath + SLASH + fileName;
            BlobId blobId = BlobId.of(appProps.bucketName(), filePath);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
            Blob blob = storage.create(blobInfo, file.getBytes());
            if (nonNull(blob)) {
                log.info(FILE_UPLOADED_SUCCESSFULLY_TO_FCS_MSG, fileName, filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean blobStorageIsDisabled() {
        return !appProps.blobStorageEnabled();
    }

    public String signedUrl(String directoryPath, String fileName) {
        if (blobStorageIsDisabled()){
            log.warn("Can't generate signed url because blog storage feature is disabled");
            return null;
        }
        String filePath = directoryPath + SLASH + fileName;
        BlobId blobId = BlobId.of(appProps.bucketName(), filePath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        URL signedUrl = storage.signUrl(blobInfo, appProps.signedUrlExpiryTimeInSec(), SECONDS);
        if (isNull(signedUrl)) {
            log.info(FAILED_TO_GENERATE_SIGN_IN_URL_MSG, filePath);
            return null;
        }
        log.info(SIGNED_URL_GENERATED_SUCCESSFULLY_MSG, filePath);
        return signedUrl.toString();
    }

}
