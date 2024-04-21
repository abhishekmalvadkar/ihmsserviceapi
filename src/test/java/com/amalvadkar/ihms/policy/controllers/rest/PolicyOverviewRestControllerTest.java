package com.amalvadkar.ihms.policy.controllers.rest;

import com.amalvadkar.ihms.common.AbstractIT;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@Sql("/policy-overview-test-data.sql")
class PolicyOverviewRestControllerTest extends AbstractIT {

    @Test
    void should_return_policy_documents_data_with_policy_categories() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .post("/api/ihms/policy/fetchpolicyoverview")
                .then()
                .body("data", hasSize(2))
                .body("success", is(true))
                .body("code", is(200))
                .body("message", is("Fetched Successfully"));

    }

    @Test
    void should_return_policy_document_drive_url_for_passed_valid_document_id() {
        var requestPayload = """
                {
                    "policyDocumentId" : 1
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(requestPayload)
                .when()
                .post("/api/ihms/policy/viewpolicydocument")
                .then()
                .body("data", is("https://docs.google.com/document/d/1hIJ9ZdXktdLtSEQx3i66v3oyO4a9bid3My5ihopKEXg/preview"))
                .body("success", is(true))
                .body("code", is(200))
                .body("message", is("Created Successfully"));

    }

    @Test
    void should_return_policy_document_not_found_error_message_with_invalid_document_id() {
        var requestPayload = """
                {
                    "policyDocumentId" : 10
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(requestPayload)
                .when()
                .post("/api/ihms/policy/viewpolicydocument")
                .then()
                .body("errors", hasSize(1))
                .body("errors[0]", is("Policy document not found with id : 10"))
                .body("success", is(false))
                .body("code", is(404));


    }

}