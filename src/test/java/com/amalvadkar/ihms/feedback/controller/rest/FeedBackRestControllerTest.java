package com.amalvadkar.ihms.feedback.controller.rest;

import com.amalvadkar.ihms.common.AbstractIT;
import com.icegreen.greenmail.util.GreenMailUtil;
import io.restassured.http.ContentType;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Sql("/feedback-test-data.sql")
class FeedBackRestControllerTest extends AbstractIT {

    @Test
    void should_create_feedback_and_send_email_to_user_with_created_feedback_id_along_with_details() {
        String jsonData = """
                {
                    "feedbackTitle" : "Ticket Order Issue",
                    "feedbackDescription" : "<p>I am facing ticket order issue</p>"
                }
                """;
        given()
                .contentType(ContentType.URLENC)
                .header("userid" , 2)
                .formParam("jsondata", jsonData)
                .when()
                .post("/api/ihms/feedback/create-feedback")
                .then()
                .body("data.feedbackId", is(notNullValue()))
                .body("success", is(true))
                .body("code", is(200))
                .body("message", is("Created Successfully"));

        await().atMost(2, SECONDS).untilAsserted(() -> {
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