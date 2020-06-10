package com.typicode.jsonplaceholder.test_cases.update_operations;

import com.typicode.jsonplaceholder.constants.TestConstants;
import com.typicode.jsonplaceholder.data_generator.RandomDataGenerator;
import com.typicode.jsonplaceholder.entities.Post;
import com.typicode.jsonplaceholder.steps.AssertionSteps;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic(value = "Put request")
@Feature(value = "Update post")
@Story(value = "Update post with correct data (positive scenario)")
@Owner(value = "Jean Ponomarev")
public class UpdatePostWithCorrectDataTest {
    private static final RandomDataGenerator dataGenerator = new RandomDataGenerator();
    private final Post post = new Post();

    @BeforeClass
    public void createRequestJson() {
        post.setId(TestConstants.VALID_POST_ID);
        post.setTitle(dataGenerator.getTitle());
        post.setBody(dataGenerator.getSentence());
        post.setUserId(TestConstants.VALID_USER_ID);
    }

    @Description(value = "Update post with correct data")
    @Severity(value = SeverityLevel.BLOCKER)
    @Test
    void testUpdatePostWithCorrectData() {
        Response response = RestAssured.given()
                .pathParam("id", TestConstants.VALID_POST_ID)
                .contentType("application/json; charset=utf-8")
                .body(post)
                .when()
                .put(TestConstants.BASE_URL_WITH_SLASH + "{id}");

        AssertionSteps.checkStatusCodeStep(response, 200);
        AssertionSteps.checkStatusLineStep(response, "HTTP/1.1 200 OK");

        AssertionSteps.checkUpdatedPostIsCorrectStep(response, post);

        AssertionSteps.checkContentTypeStep(response, "application/json; charset=utf-8");
        AssertionSteps.checkResponseTimeStep(response, TestConstants.CRITICAL_RESPONSE_TIME);
        AssertionSteps.checkServerTypeStep(response, "cloudflare");
    }
}
