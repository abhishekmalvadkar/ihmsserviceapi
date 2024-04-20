package com.amalvadkar.ihms.common.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder
@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CustomResModel {
    private Object data;
    private String message;
    private List<String> errors;
    private Boolean success;
    private Integer code;

    public static CustomResModel success(Object data, String message){
        return CustomResModel.builder()
                .data(data)
                .success(true)
                .message(message)
                .code(HttpStatus.OK.value())
                .build();
    }

    public static CustomResModel fail(List<String> errors , HttpStatus httpStatus) {
        return CustomResModel.builder()
                .errors(errors)
                .success(false)
                .code(httpStatus.value())
                .build();
    }
}
