package com.amalvadkar.ihms.common.exceptions.handlers;

import com.amalvadkar.ihms.common.exceptions.ResourceNotFoundException;
import com.amalvadkar.ihms.common.models.response.CustomResModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.amalvadkar.ihms.common.utils.AppConstants.SOMETHING_WENT_WRONG_ERROR_MESSAGE;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public CustomResModel handleResourceNotFoundException(ResourceNotFoundException ex){
        List<String> errorMessages = List.of(ex.getMessage());
        return CustomResModel.fail(errorMessages , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public CustomResModel handleException(Exception ex){
        List<String> errorMessages = List.of(SOMETHING_WENT_WRONG_ERROR_MESSAGE);
        return CustomResModel.fail(errorMessages , HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
