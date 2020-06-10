package com.typicode.jsonplaceholder.test_cases.get_operations.get_posts_by_user_id;

import com.typicode.jsonplaceholder.constants.TestConstants;
import com.typicode.jsonplaceholder.data_generator.RandomDataGenerator;
import com.typicode.jsonplaceholder.entities.Post;
import com.typicode.jsonplaceholder.steps.AssertionSteps;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

@Epic(value = "Get request")
@Feature(value = "Get post by userId")
@Owner(value = "Jean Ponomarev")
public class GetPostsByValidUserIdTest {
    private Response response;
    private List<Post> postsList;

    private static final RandomDataGenerator dataGenerator = new RandomDataGenerator();

    private static final int VALID_USER_ID = dataGenerator.getIdFromRange(1, 10);

    @BeforeClass
    void getResponse() {
        response = RestAssured.given()
                .queryParam("userId", VALID_USER_ID)
                .when()
                .get("https://jsonplaceholder.typicode.com/posts");

        postsList = Arrays.asList(response.getBody().as(Post[].class));
    }

    @Story(value = "Get post by userId, positive scenario")
    @Description(value = "Get post by valid userId")
    @Severity(value = SeverityLevel.BLOCKER)
    @Test
    void testGetPostsByValidUserId() {
        AssertionSteps.checkPostsListHasCorrectUserIdStep(postsList, VALID_USER_ID);
        AssertionSteps.checkPostsListLengthStep(postsList, TestConstants.USERS_POST_AMOUNT);
        AssertionSteps.checkPostsListIsSortedByIdStep(postsList);

        AssertionSteps.checkStatusCodeStep(response, 200);
        AssertionSteps.checkStatusLineStep(response, "HTTP/1.1 200 OK");
        AssertionSteps.checkContentTypeStep(response, "application/json; charset=utf-8");
        AssertionSteps.checkResponseTimeStep(response, TestConstants.CRITICAL_RESPONSE_TIME);
        AssertionSteps.checkServerTypeStep(response, "cloudflare");
        AssertionSteps.checkContentEncodingStep(response, "gzip");
    }
}
