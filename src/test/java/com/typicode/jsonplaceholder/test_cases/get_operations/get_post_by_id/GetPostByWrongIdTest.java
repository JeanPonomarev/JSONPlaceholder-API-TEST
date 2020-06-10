package com.typicode.jsonplaceholder.test_cases.get_operations.get_post_by_id;

import com.typicode.jsonplaceholder.constants.TestConstants;
import com.typicode.jsonplaceholder.data_generator.RandomDataGenerator;
import com.typicode.jsonplaceholder.steps.AssertionSteps;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

@Epic(value = "Get request")
@Feature(value = "Get post by id")
@Story(value = "Get post by wrong id (negative scenario)")
@Owner(value = "Jean Ponomarev")
public class GetPostByWrongIdTest {
    private final RandomDataGenerator dataGenerator = new RandomDataGenerator();

    @Description(value = "Get post by id that doesn't exist")
    @Severity(value = SeverityLevel.CRITICAL)
    @Test
    void testGetPostByNonexistentId() {
        Response response = RestAssured.given()
                .pathParam("id", TestConstants.NONEXISTENT_POST_ID)
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/{id}");

        AssertionSteps.checkStatusCodeStep(response, 404);
        AssertionSteps.checkStatusLineStep(response, "HTTP/1.1 404 Not Found");
        AssertionSteps.checkResponseTimeStep(response, TestConstants.CRITICAL_RESPONSE_TIME);
        AssertionSteps.checkServerTypeStep(response, "cloudflare");
        AssertionSteps.checkContentEncodingStep(response, null);
    }

    @Description(value = "Get post by invalid id: random string instead of number")
    @Severity(value = SeverityLevel.NORMAL)
    @Test
    void testGetPostByInvalidId() {
        Response response = RestAssured.given()
                .pathParam("id", dataGenerator.getNormalRandomString())
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/{id}");

        AssertionSteps.checkStatusCodeStep(response, 400, 404);
        AssertionSteps.checkStatusLineStep(response, "HTTP/1.1 400 Bad Request", "HTTP/1.1 404 Not Found");
        AssertionSteps.checkResponseTimeStep(response, TestConstants.CRITICAL_RESPONSE_TIME);
        AssertionSteps.checkServerTypeStep(response, "cloudflare");
        AssertionSteps.checkContentEncodingStep(response, null);
    }

    @Description(value = "Get post by large id (to check that server can handle it correctly)")
    @Severity(value = SeverityLevel.NORMAL)
    @Test
    void testGetPostByLargeId() {
        Response response = RestAssured.given()
                .pathParam("id", dataGenerator.getLargeId())
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/{id}");

        AssertionSteps.checkStatusCodeStep(response, 404);
        AssertionSteps.checkStatusLineStep(response, "HTTP/1.1 404 Not Found");
        AssertionSteps.checkResponseTimeStep(response, TestConstants.CRITICAL_RESPONSE_TIME);
        AssertionSteps.checkServerTypeStep(response, "cloudflare");
        AssertionSteps.checkContentEncodingStep(response, null);
    }
}
