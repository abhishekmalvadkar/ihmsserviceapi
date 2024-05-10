package com.amalvadkar.ihms.holiday.controllers.rest;

import com.amalvadkar.ihms.common.AbstractIT;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@Sql("/holiday-overview-test-data.sql")
@Sql("/add_test_user_and_delete_all_users_except_system_user.sql")
class HolidayOverviewRestControllerTest extends AbstractIT {

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
        var requestPayload = """
                {
                    "countryId" : null
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(requestPayload)
                .when()
                .post("/api/ihms/holiday/fetch-holiday-overview")
                .then()
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
    }

    @Test
    void should_return_holiday_list_for_passed_country_id() {
        var requestPayload = """
                {
                    "countryId" : 2
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(requestPayload)
                .when()
                .post("/api/ihms/holiday/fetch-holiday-overview")
                .then()
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
                .body("data.content[1].holidayDate", is("27 May 2024"))
                .body("data.content[1].holidayTitle", is("Memorial Day"))
                .body("data.content[1].isOptional", is("No"))
                .body("data.content[1].country", is("United States of America"))
                .body("data.content[1].holidayStatusColor", is("#808080"))
                .body("data.content[1].holidayStatus", is("Upcoming"))
                .body("data.content[1].day", is("Monday"))
                .body("success", is(true))
                .body("code", is(200))
                .body("message", is("Fetched Successfully"));
    }

    @Test
    void should_return_empty_holiday_list_for_passed_invalid_country_id() {
        var requestPayload = """
                {
                    "countryId" : 5000
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(requestPayload)
                .when()
                .post("/api/ihms/holiday/fetch-holiday-overview")
                .then()
                .body("data.content", is(empty()))
                .body("success", is(true))
                .body("code", is(200))
                .body("message", is("Fetched Successfully"));
    }


}