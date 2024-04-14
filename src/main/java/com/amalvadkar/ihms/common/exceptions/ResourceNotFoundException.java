package com.amalvadkar.ihms.common.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message){
        super(message);
    }

    public static ResourceNotFoundException from(String entityName , String propertyName,  String propertyValue){
        String message = String.format("%s not found with %s : %s", entityName, propertyName, propertyValue);
        return new ResourceNotFoundException(message);
    }

}
