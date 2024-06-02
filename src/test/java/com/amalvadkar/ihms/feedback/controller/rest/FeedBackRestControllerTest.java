package com.amalvadkar.ihms.feedback.controller.rest;

import com.amalvadkar.ihms.common.AbstractIT;
import com.amalvadkar.ihms.common.entities.FileMetadataEntity;
import com.amalvadkar.ihms.common.repositories.FileMetadataRepository;
import com.icegreen.greenmail.util.GreenMailUtil;
import io.restassured.http.ContentType;
import io.restassured.internal.util.IOUtils;
import io.restassured.response.Response;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static io.restassured.RestAssured.given;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@Sql("/feedback-test-data.sql")
@Sql("/add_test_user_and_delete_all_users_except_system_user.sql")
class FeedBackRestControllerTest extends AbstractIT {

    @Autowired
    FileMetadataRepository fileMetadataRepo;

    @Test
    void should_create_feedback_and_send_email_to_user_with_created_feedback_id_along_with_details_and_save_files_meta_data_and_not_upload_to_cloud_in_test_env() throws IOException {
        String jsonData = """
                {
                    "feedbackTitle" : "Ticket Order Issue",
                    "feedbackDescription" : "<p>I am facing ticket order issue</p>"
                }
                """;
        ClassPathResource img = new ClassPathResource("img.png");
        InputStream inputStream = img.getInputStream();
        byte[] imgBytes = IOUtils.toByteArray(inputStream);

        Response response = given()
                .contentType(ContentType.MULTIPART)
                .header("userid", 2)
                .formParam("jsondata", jsonData)
                .multiPart("files", "img.png", imgBytes)
                .when()
                .post("/api/ihms/feedback/create-feedback")
                .then()
                .extract()
                .response();

        boolean success = response.path("success");
        assertThat(success).isTrue();

        String message = response.path("message");
        assertThat(message).isEqualTo("Created Successfully");

        int code = response.path("code");
        assertThat(code).isEqualTo(200);

        String newCreatedFeedbackId = response.path("data.feedbackId");

        List<FileMetadataEntity> fileMetadataEntityList = fileMetadataRepo.findAllByRecordId(newCreatedFeedbackId);

        assertThat(fileMetadataEntityList).hasSize(1);
        assertThat(fileMetadataEntityList.getFirst().getPath()).isEqualTo("feedback-images/" + newCreatedFeedbackId);
        assertThat(fileMetadataEntityList.getFirst().getFileName()).isEqualTo("img.png");


        await().atMost(3, SECONDS).untilAsserted(() -> {
            MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
            assertThat(receivedMessages).hasSize(1);

            MimeMessage receivedMessage = receivedMessages[0];
            String mailBody = GreenMailUtil.getBody(receivedMessage);
            assertThat(mailBody).contains("Ticket Order Issue");
            assertThat(mailBody).contains("<p>I am facing ticket order issue</p>");
            assertThat(mailBody).contains("SUBMITTED");
            assertThat(receivedMessage.getAllRecipients()).hasSize(1);
            assertThat(receivedMessage.getAllRecipients()[0].toString()).isEqualTo("it@test.com");
        });
    }
}
