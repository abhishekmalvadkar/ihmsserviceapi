package com.amalvadkar.ihms.common.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonHelper {

    private final ObjectMapper objectMapper;

    public <T> T jsonToObj(String json, Class<T> resultClass) {
        try {
            return objectMapper.readValue(json, resultClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
