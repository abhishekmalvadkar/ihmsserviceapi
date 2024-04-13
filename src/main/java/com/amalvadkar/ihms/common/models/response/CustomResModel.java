package com.amalvadkar.ihms.common.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CustomResModel {
    private Object data;
    private String message;
    private List<String> errors;
    private Boolean status;
    private Integer code;
}
