package com.typicode.jsonplaceholder.test_cases.delete_operations;

import com.typicode.jsonplaceholder.constants.TestConstants;
import com.typicode.jsonplaceholder.data_generator.RandomDataGenerator;
import com.typicode.jsonplaceholder.steps.AssertionSteps;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

@Epic(value = "Delete request")
@Feature(value = "Delete post")
@Owner(value = "Jean Ponomarev")
public class DeletePostTest {
    private static final RandomDataGenerator dataGenerator = new RandomDataGenerator();
    private static final int VALID_POST_ID = dataGenerator.getIdFromRange(1, 100);

    @Story(value = "Delete existent post (positive scenario)")
    @Description(value = "Delete existent post by valid id")
    @Severity(value = SeverityLevel.BLOCKER)
    @Test
    void testDeleteExistentPost() {
        Response response = RestAssured.given()
                .pathParam("id", VALID_POST_ID)
                .when()
                .delete(TestConstants.BASE_URL_WITH_SLASH + "{id}");

        AssertionSteps.checkStatusCodeStep(response, 200);
        AssertionSteps.checkStatusLineStep(response, "HTTP/1.1 200 OK");
        AssertionSteps.checkResponseTimeStep(response, TestConstants.CRITICAL_RESPONSE_TIME);
        AssertionSteps.checkServerTypeStep(response, "cloudflare");
    }

    @Story(value = "Delete post using wrong id in the URL path (negative scenario)")
    @Description(value = "Delete post by nonexistent id")
    @Severity(value = SeverityLevel.BLOCKER)
    @Test
    void testDeleteNonexistentPost() {
        Response response = RestAssured.given()
                .pathParam("id", TestConstants.NONEXISTENT_POST_ID)
                .when()
                .delete(TestConstants.BASE_URL_WITH_SLASH + "{id}");

        AssertionSteps.checkStatusCodeStep(response, 404);
        AssertionSteps.checkStatusLineStep(response, "HTTP/1.1 404 Not Found");
        AssertionSteps.checkResponseTimeStep(response, TestConstants.CRITICAL_RESPONSE_TIME);
        AssertionSteps.checkServerTypeStep(response, "cloudflare");
    }

    @Story(value = "Delete post using wrong id in the URL path (negative scenario)")
    @Description(value = "Delete post by incorrect id: String value")
    @Severity(value = SeverityLevel.BLOCKER)
    @Test
    void testDeletePostByIncorrectId() {
        Response response = RestAssured.given()
                .pathParam("id", dataGenerator.getNormalRandomString())
                .when()
                .delete(TestConstants.BASE_URL_WITH_SLASH + "{id}");

        AssertionSteps.checkStatusCodeStep(response, 404, 400);
        AssertionSteps.checkStatusLineStep(response, "HTTP/1.1 404 Not Found", "HTTP/1.1 400 Bad Request");
        AssertionSteps.checkResponseTimeStep(response, TestConstants.CRITICAL_RESPONSE_TIME);
        AssertionSteps.checkServerTypeStep(response, "cloudflare");
    }

    @Story(value = "Delete post using wrong id in the URL path (negative scenario)")
    @Description(value = "Delete post using large id")
    @Severity(value = SeverityLevel.BLOCKER)
    @Test
    void testDeletePostByLargeId() {
        Response response = RestAssured.given()
                .pathParam("id", dataGenerator.getLargeId())
                .when()
                .delete(TestConstants.BASE_URL_WITH_SLASH + "{id}");

        AssertionSteps.checkStatusCodeStep(response, 404, 400);
        AssertionSteps.checkStatusLineStep(response, "HTTP/1.1 404 Not Found", "HTTP/1.1 400 Bad Request");
        AssertionSteps.checkResponseTimeStep(response, TestConstants.CRITICAL_RESPONSE_TIME);
        AssertionSteps.checkServerTypeStep(response, "cloudflare");
    }
}
