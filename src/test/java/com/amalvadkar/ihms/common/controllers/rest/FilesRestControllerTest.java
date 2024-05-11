package com.amalvadkar.ihms.common.controllers.rest;

import com.amalvadkar.ihms.common.AbstractIT;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@Sql("/files-test-data.sql")
@Sql("/add_test_user_and_delete_all_users_except_system_user.sql")
class FilesRestControllerTest extends AbstractIT {

    @Test
    void view_file_should_return_signed_url_as_null_because_blog_storage_feature_is_disable_for_testing_env() {
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

    @Test
    void fetch_files_should_return_files_metadata_for_passed_valid_record_id() {
        var requestPayload = """
                {
                    "recordId" : "01HXG3VPD756QV0456BV2NW1B2"
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(requestPayload)
                .when()
                .post("/api/ihms/files/fetch-files")
                .then()
                .body("data.content", hasSize(2))
                .body("data.content[0].fileId", is("01HXG3VPFKKZAZ5134DYBTPDPF"))
                .body("data.content[0].fileName", is("img_1.png"))
                .body("data.content[0].uploadedBy", is("Test"))
                .body("data.content[0].uploadedOn", is("2024-05-11T12:23:33Z"))
                .body("data.content[1].fileId", is("01HXG3VPFMFPC0WP5WK01CSH3H"))
                .body("data.content[1].fileName", is("img_2.png"))
                .body("data.content[1].uploadedBy", is("Test"))
                .body("data.content[1].uploadedOn", is("2024-05-11T12:23:33Z"))
                .body("success", is(true))
                .body("code", is(200))
                .body("message", is("Fetched Successfully"));

    }

    @Test
    void fetch_files_should_return_empty_content_if_no_files_found_for_passed_record_id() {
        var requestPayload = """
                {
                    "recordId" : "01HXG3VPD756AV0456BV2NW1B2"
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(requestPayload)
                .when()
                .post("/api/ihms/files/fetch-files")
                .then()
                .body("data.content", is(empty()))
                .body("success", is(true))
                .body("code", is(200))
                .body("message", is("No Files"));

    }

}