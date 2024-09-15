package com.amalvadkar.ihms.holiday.controllers.rest;

import com.amalvadkar.ihms.TestUtils;
import com.amalvadkar.ihms.app.client.RedisCacheClient;
import com.amalvadkar.ihms.app.models.dto.CacheMetaData;
import com.amalvadkar.ihms.common.AbstractIT;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@Sql("/holiday-overview-test-data.sql")
class HolidayOverviewRestControllerTest extends AbstractIT {

    @Autowired
    RedisCacheClient redisCacheClient;

    @Test
    void should_return_country_list_and_holiday_status_list_as_holiday_overview_metadata() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .post("/api/ihms/holiday/fetch-holiday-overview-metadata")
                .then()
                .body("data.metaData.countryList", hasSize(2))
                .body("data.metaData.countryList[0].key", is(1))
                .body("data.metaData.countryList[0].value", is("India"))
                .body("data.metaData.countryList[1].key", is(2))
                .body("data.metaData.countryList[1].value", is("United States of America"))
                .body("data.metaData.holidayStatusList", hasSize(2))
                .body("data.metaData.holidayStatusList[0].title", is("Upcoming"))
                .body("data.metaData.holidayStatusList[0].colorCode", is("#808080"))
                .body("data.metaData.holidayStatusList[0].code", is("UPC"))
                .body("data.metaData.holidayStatusList[1].title", is("Has Gone"))
                .body("data.metaData.holidayStatusList[1].colorCode", is("#FF0000"))
                .body("data.metaData.holidayStatusList[1].code", is("HG"))
                .body("success", is(true))
                .body("code", is(200))
                .body("message", is("Fetched Successfully"));

    }

    @Test
    void should_return_holiday_list_for_first_country_from_country_dropdown_if_country_id_is_not_passed() {
        RestAssured.registerParser("text/plain", Parser.JSON);
        var endPointUrl = "/api/ihms/holiday/fetch-holiday-overview";
        var requestPayload = """
                {
                    "countryId" : null
                }
                """;
        var contentForRequestHash = """
                {
                    "url" : %s,
                    "method" : "POST"
                    "payload" : %s
                }
                """.formatted(endPointUrl ,requestPayload);
        String requestSha256Hash = TestUtils.toSha256Hash(contentForRequestHash);

        given()
                .contentType(ContentType.JSON)
                .header("X-Request-Hash",requestSha256Hash)
                .header("roleId",1)
                .body(requestPayload)
                .when()
                .post(endPointUrl)
                .then()
                .header("Content-Cached", "cache-miss")
                .body("data.headers", hasSize(7))
                .body("data.content", hasSize(2))
                .body("data.content[0].holidayId", is(1))
                .body("data.content[0].holidayDate", is("14 Jan 2024"))
                .body("data.content[0].holidayTitle", is("Makar Sankranti"))
                .body("data.content[0].isOptional", is("No"))
                .body("data.content[0].country", is("India"))
                .body("data.content[0].holidayStatusColor", is("#FF0000"))
                .body("data.content[0].holidayStatus", is("Has Gone"))
                .body("data.content[0].day", is("Sunday"))
                .body("data.content[1].holidayId", is(2))
                .body("data.content[1].holidayDate", is("15 Jan 2024"))
                .body("data.content[1].holidayTitle", is("Vasi Utarayan"))
                .body("data.content[1].isOptional", is("No"))
                .body("data.content[1].country", is("India"))
                .body("data.content[1].holidayStatusColor", is("#FF0000"))
                .body("data.content[1].holidayStatus", is("Has Gone"))
                .body("data.content[1].day", is("Monday"))
                .body("success", is(true))
                .body("code", is(200))
                .body("message", is("Fetched Successfully"));


        await().atMost(5, SECONDS).untilAsserted(() -> {
            given()
                    .contentType(ContentType.JSON)
                    .header("X-Request-Hash",requestSha256Hash)
                    .header("roleId",1)
                    .body(requestPayload)
                    .when()
                    .post(endPointUrl)
                    .then()
                    .header("Content-Cached", "cache-hit")
                    .body("data.headers", hasSize(7))
                    .body("data.content", hasSize(2))
                    .body("data.content[0].holidayId", is(1))
                    .body("data.content[0].holidayDate", is("14 Jan 2024"))
                    .body("data.content[0].holidayTitle", is("Makar Sankranti"))
                    .body("data.content[0].isOptional", is("No"))
                    .body("data.content[0].country", is("India"))
                    .body("data.content[0].holidayStatusColor", is("#FF0000"))
                    .body("data.content[0].holidayStatus", is("Has Gone"))
                    .body("data.content[0].day", is("Sunday"))
                    .body("data.content[1].holidayId", is(2))
                    .body("data.content[1].holidayDate", is("15 Jan 2024"))
                    .body("data.content[1].holidayTitle", is("Vasi Utarayan"))
                    .body("data.content[1].isOptional", is("No"))
                    .body("data.content[1].country", is("India"))
                    .body("data.content[1].holidayStatusColor", is("#FF0000"))
                    .body("data.content[1].holidayStatus", is("Has Gone"))
                    .body("data.content[1].day", is("Monday"))
                    .body("success", is(true))
                    .body("code", is(200))
                    .body("message", is("Fetched Successfully"));
        });

        CacheMetaData cacheMetaData = new CacheMetaData(requestSha256Hash, "fetch-holiday-overview", "3600");
        await().atMost(5, SECONDS).untilAsserted(() -> {
            Map<String, String> cacheStats = redisCacheClient.getCacheStats(cacheMetaData);
            assertThat(cacheStats).hasSize(3);
            assertThat(cacheStats).containsOnlyKeys("response", "cache_hit", "cache_miss");
            assertThat(cacheStats.get("response")).isNotNull();
            assertThat(cacheStats).containsEntry("cache_hit", "1");
            assertThat(cacheStats.get("cache_miss")).isGreaterThanOrEqualTo("1");
        });
    }

    @Test
    void should_return_holiday_list_for_passed_country_id() {
        RestAssured.registerParser("text/plain", Parser.JSON);
        var endPointUrl = "/api/ihms/holiday/fetch-holiday-overview";
        var requestPayload = """
                {
                    "countryId" : 2
                }
                """;
        var contentForRequestHash = """
                {
                    "url" : %s,
                    "method" : "POST"
                    "payload" : %s
                }
                """.formatted(endPointUrl ,requestPayload);
        String requestSha256Hash = TestUtils.toSha256Hash(contentForRequestHash);
        given()
                .contentType(ContentType.JSON)
                .header("X-Request-Hash",requestSha256Hash)
                .header("roleId",1)
                .body(requestPayload)
                .when()
                .post(endPointUrl)
                .then()
                .header("Content-Cached", "cache-miss")
                .body("data.headers", hasSize(7))
                .body("data.content", hasSize(2))
                .body("data.content[0].holidayId", is(3))
                .body("data.content[0].holidayDate", is("01 Jan 2024"))
                .body("data.content[0].holidayTitle", is("New Year's Day"))
                .body("data.content[0].isOptional", is("No"))
                .body("data.content[0].country", is("United States of America"))
                .body("data.content[0].holidayStatusColor", is("#FF0000"))
                .body("data.content[0].holidayStatus", is("Has Gone"))
                .body("data.content[0].day", is("Monday"))
                .body("data.content[1].holidayId", is(4))
                .body("data.content[1].holidayDate", is("27 May 2090"))
                .body("data.content[1].holidayTitle", is("Memorial Day"))
                .body("data.content[1].isOptional", is("No"))
                .body("data.content[1].country", is("United States of America"))
                .body("data.content[1].holidayStatusColor", is("#808080"))
                .body("data.content[1].holidayStatus", is("Upcoming"))
                .body("data.content[1].day", is("Saturday"))
                .body("success", is(true))
                .body("code", is(200))
                .body("message", is("Fetched Successfully"));

        await().atMost(5, SECONDS).untilAsserted(() -> {
            given()
                    .contentType(ContentType.JSON)
                    .header("X-Request-Hash",requestSha256Hash)
                    .header("roleId",1)
                    .body(requestPayload)
                    .when()
                    .post(endPointUrl)
                    .then()
                    .header("Content-Cached", "cache-hit")
                    .body("data.headers", hasSize(7))
                    .body("data.content", hasSize(2))
                    .body("data.content[0].holidayId", is(3))
                    .body("data.content[0].holidayDate", is("01 Jan 2024"))
                    .body("data.content[0].holidayTitle", is("New Year's Day"))
                    .body("data.content[0].isOptional", is("No"))
                    .body("data.content[0].country", is("United States of America"))
                    .body("data.content[0].holidayStatusColor", is("#FF0000"))
                    .body("data.content[0].holidayStatus", is("Has Gone"))
                    .body("data.content[0].day", is("Monday"))
                    .body("data.content[1].holidayId", is(4))
                    .body("data.content[1].holidayDate", is("27 May 2090"))
                    .body("data.content[1].holidayTitle", is("Memorial Day"))
                    .body("data.content[1].isOptional", is("No"))
                    .body("data.content[1].country", is("United States of America"))
                    .body("data.content[1].holidayStatusColor", is("#808080"))
                    .body("data.content[1].holidayStatus", is("Upcoming"))
                    .body("data.content[1].day", is("Saturday"))
                    .body("success", is(true))
                    .body("code", is(200))
                    .body("message", is("Fetched Successfully"));
        });

        CacheMetaData cacheMetaData = new CacheMetaData(requestSha256Hash, "fetch-holiday-overview", "3600");
        await().atMost(5, SECONDS).untilAsserted(() -> {
            Map<String, String> cacheStats = redisCacheClient.getCacheStats(cacheMetaData);
            assertThat(cacheStats).hasSize(3);
            assertThat(cacheStats).containsOnlyKeys("response", "cache_hit", "cache_miss");
            assertThat(cacheStats.get("response")).isNotNull();
            assertThat(cacheStats).containsEntry("cache_hit", "1");
            assertThat(cacheStats.get("cache_miss")).isGreaterThanOrEqualTo("1");
        });
    }

    @Test
    void should_return_empty_holiday_list_for_passed_invalid_country_id() {
        RestAssured.registerParser("text/plain", Parser.JSON);
        var endPointUrl = "/api/ihms/holiday/fetch-holiday-overview";
        var requestPayload = """
                {
                    "countryId" : 5000
                }
                """;
        var contentForRequestHash = """
                {
                    "url" : %s,
                    "method" : "POST"
                    "payload" : %s
                }
                """.formatted(endPointUrl ,requestPayload);
        String requestSha256Hash = TestUtils.toSha256Hash(contentForRequestHash);
        given()
                .contentType(ContentType.JSON)
                .header("X-Request-Hash",requestSha256Hash)
                .header("roleId",1)
                .body(requestPayload)
                .when()
                .post(endPointUrl)
                .then()
                .header("Content-Cached", "cache-miss")
                .body("data.headers", hasSize(7))
                .body("data.content", is(empty()))
                .body("success", is(true))
                .body("code", is(200))
                .body("message", is("Fetched Successfully"));

        await().atMost(5, SECONDS).untilAsserted(() -> {
            given()
                    .contentType(ContentType.JSON)
                    .header("X-Request-Hash",requestSha256Hash)
                    .header("roleId",1)
                    .body(requestPayload)
                    .when()
                    .post(endPointUrl)
                    .then()
                    .header("Content-Cached", "cache-hit")
                    .body("data.headers", hasSize(7))
                    .body("data.content", is(empty()))
                    .body("success", is(true))
                    .body("code", is(200))
                    .body("message", is("Fetched Successfully"));
        });

        CacheMetaData cacheMetaData = new CacheMetaData(requestSha256Hash, "fetch-holiday-overview", "3600");
        await().atMost(5, SECONDS).untilAsserted(() -> {
            Map<String, String> cacheStats = redisCacheClient.getCacheStats(cacheMetaData);
            assertThat(cacheStats).hasSize(3);
            assertThat(cacheStats).containsOnlyKeys("response", "cache_hit", "cache_miss");
            assertThat(cacheStats.get("response")).isNotNull();
            assertThat(cacheStats).containsEntry("cache_hit", "1");
            assertThat(cacheStats.get("cache_miss")).isGreaterThanOrEqualTo("1");
        });

    }


}
