package com.typicode.jsonplaceholder.test_cases.get_operations.get_posts_by_user_id;

import com.typicode.jsonplaceholder.constants.TestConstants;
import com.typicode.jsonplaceholder.data_generator.RandomDataGenerator;
import com.typicode.jsonplaceholder.steps.AssertionSteps;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

@Epic(value = "Get request")
@Feature(value = "Get posts by userId")
@Story(value = "Get posts by wrong userId (negative scenario)")
@Owner(value = "Jean Ponomarev")
public class GetPostsByWrongUserIdTest {
    private final RandomDataGenerator dataGenerator = new RandomDataGenerator();

    @Description(value = "Get post by userId that doesn't exist")
    @Severity(value = SeverityLevel.CRITICAL)
    @Test
    void testGetPostByNonexistentUserId() {
        Response response = RestAssured.given()
                .queryParam("userId", TestConstants.NONEXISTENT_USER_ID)
                .when()
                .get("https://jsonplaceholder.typicode.com/posts");

        AssertionSteps.checkStatusCodeStep(response, 404);
        AssertionSteps.checkStatusLineStep(response, "HTTP/1.1 404 Not Found");
        AssertionSteps.checkResponseTimeStep(response, TestConstants.CRITICAL_RESPONSE_TIME);
        AssertionSteps.checkServerTypeStep(response, "cloudflare");
        AssertionSteps.checkContentEncodingStep(response, null);
    }

    @Description(value = "Get posts by invalid userId: random string instead of number")
    @Severity(value = SeverityLevel.NORMAL)
    @Test
    void testGetPostByInvalidUserId() {
        Response response = RestAssured.given()
                .queryParam("userId", dataGenerator.getNormalRandomString())
                .when()
                .get("https://jsonplaceholder.typicode.com/posts");

        AssertionSteps.checkStatusCodeStep(response, 400, 404);
        AssertionSteps.checkStatusLineStep(response, "HTTP/1.1 400 Bad Request", "HTTP/1.1 404 Not Found");
        AssertionSteps.checkResponseTimeStep(response, TestConstants.CRITICAL_RESPONSE_TIME);
        AssertionSteps.checkServerTypeStep(response, "cloudflare");
        AssertionSteps.checkContentEncodingStep(response, null);
    }

    @Description(value = "Get posts by large userId (to check that server can handle it correctly)")
    @Severity(value = SeverityLevel.NORMAL)
    @Test
    void testGetPostByLargeUserId() {
        Response response = RestAssured.given()
                .queryParam("userId", dataGenerator.getLargeId())
                .when()
                .get("https://jsonplaceholder.typicode.com/posts");

        AssertionSteps.checkStatusCodeStep(response, 404);
        AssertionSteps.checkStatusLineStep(response, "HTTP/1.1 404 Not Found");
        AssertionSteps.checkResponseTimeStep(response, TestConstants.CRITICAL_RESPONSE_TIME);
        AssertionSteps.checkServerTypeStep(response, "cloudflare");
        AssertionSteps.checkContentEncodingStep(response, null);
    }
}
