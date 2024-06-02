package com.amalvadkar.ihms;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
@Slf4j
public class TestConfig {

    @Bean("fireBaseStorage")
    public Storage fireBaseStorage() {
        log.info("Creating storage object for test env");
        return StorageOptions.newBuilder().build().getService();
    }

}
