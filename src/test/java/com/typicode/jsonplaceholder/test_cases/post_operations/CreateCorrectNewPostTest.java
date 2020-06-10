package com.typicode.jsonplaceholder.test_cases.post_operations;

import com.typicode.jsonplaceholder.constants.TestConstants;
import com.typicode.jsonplaceholder.data_generator.RandomDataGenerator;
import com.typicode.jsonplaceholder.entities.Post;
import com.typicode.jsonplaceholder.steps.AssertionSteps;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic(value = "Post request")
@Feature(value = "Create a new post")
@Story(value = "Create post with correct data (positive scenario)")
@Owner(value = "Jean Ponomarev")
public class CreateCorrectNewPostTest {
    private static final RandomDataGenerator dataGenerator = new RandomDataGenerator();
    private final Post post = new Post();

    @BeforeClass
    public void createRequestJson() {
        post.setTitle(dataGenerator.getTitle());
        post.setBody(dataGenerator.getSentence());
        post.setUserId(dataGenerator.getIdFromRange(1, 10));
    }

    @Description(value = "Create a new post with correct title, body and existent userId")
    @Severity(value = SeverityLevel.BLOCKER)
    @Test
    void testCreatePostWithCorrectData() {
        Response response = RestAssured.given()
                .contentType("application/json; charset=utf-8")
                .body(post)
                .when()
                .post(TestConstants.BASE_URL);

        AssertionSteps.checkStatusCodeStep(response, 201);
        AssertionSteps.checkStatusLineStep(response, "HTTP/1.1 201 Created");
        AssertionSteps.checkCreatedPostIsCorrectStep(response, post);
        AssertionSteps.checkContentTypeStep(response, "application/json; charset=utf-8");
        AssertionSteps.checkResponseTimeStep(response, TestConstants.CRITICAL_RESPONSE_TIME);
        AssertionSteps.checkServerTypeStep(response, "cloudflare");
    }
}
