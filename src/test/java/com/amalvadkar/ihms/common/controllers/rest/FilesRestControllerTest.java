package com.amalvadkar.ihms.common.controllers.rest;

import com.amalvadkar.ihms.common.AbstractIT;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@Sql("/files-test-data.sql")
class FilesRestControllerTest extends AbstractIT {

    @Test
    void should_return_signed_url_as_null_because_blog_storage_feature_is_disable_for_testing_env() {
        var requestPayload = """
                {
                    "fileId" : "01HXG3VPFKKZAZ5134DYBTPDPF"
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(requestPayload)
                .when()
                .post("/api/ihms/files/view-file")
                .then()
                .body("data", is(nullValue()))
                .body("success", is(true))
                .body("code", is(200))
                .body("message", is("Created Successfully"));

    }

}