package com.amalvadkar.ihms.common.utils;

public class AppConstants {
    public static final String REQUEST_HEADER_USER_ID = "userid";
    public static final String FETCHED_SUCCESSFULLY_RESPONSE_MESSAGE = "Fetched Successfully";
    public static final String CREATED_SUCCESSFULLY_RESPONSE_MESSAGE = "Created Successfully";
    public static final String SOMETHING_WENT_WRONG_ERROR_MESSAGE = "Something went wrong, please contact to admin..";
    public static final String COUNTRY_LIST = "countryList";
    public static final String HOLIDAY_STATUS_LIST = "holidayStatusList";
    public static final String HOLIDAY_STATUS = "Holiday Status";
    public static final String CODE = "code";
    public static final String FORM_DATA_JSON_DATA = "jsondata";
    public static final String FEEDBACK_ID = "feedbackId";
    public static final String USERNAME = "username";
    public static final String FEEDBACK_TITLE = "feedbackTitle";
    public static final String FEEDBACK_DESCRIPTION = "feedbackDescription";
    public static final String FEEDBACK_STATUS = "feedbackStatus";
    public static final String EMAIL_SUBJECT_FEEDBACK_ADDED_SUCCESSFULLY = "Feedback Added Successfully";
    public static final String CREATE_FEEDBACK_SUCCESS_EMAIL_TEMPLATE_NAME = "create-feedback-success-email";

    private AppConstants() {
        throw new IllegalStateException("You can't create object for AppConstants utility class");
    }
}
