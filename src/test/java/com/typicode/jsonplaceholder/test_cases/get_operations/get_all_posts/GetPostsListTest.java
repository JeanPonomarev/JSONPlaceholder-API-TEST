package com.typicode.jsonplaceholder.test_cases.get_operations.get_all_posts;

import com.typicode.jsonplaceholder.constants.TestConstants;
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
@Feature(value = "Get list of all posts")
@Owner(value = "Jean Ponomarev")
public class GetPostsListTest {
    private Response response;
    private List<Post> postsList;

    @BeforeClass
    void getResponseAndPostsList() {
        response = RestAssured
                .given()
                .when()
                .get(TestConstants.BASE_URL);

        postsList = Arrays.asList(response.getBody().as(Post[].class));
    }

    @Story(value = "Get list of all posts by positive scenario")
    @Description(value = "Get list of all posts")
    @Severity(value = SeverityLevel.BLOCKER)
    @Test
    public void testGetAllPostsFromDatabase() {
        AssertionSteps.checkPostsListLengthStep(postsList, TestConstants.POSTS_AMOUNT);
        AssertionSteps.checkPostsListIsSortedByIdStep(postsList);
        AssertionSteps.checkPostsListObjectHasOnlyNotNullFieldsStep(postsList);

        AssertionSteps.checkStatusCodeStep(response, 200);
        AssertionSteps.checkStatusLineStep(response, "HTTP/1.1 200 OK");
        AssertionSteps.checkContentTypeStep(response, "application/json; charset=utf-8");
        AssertionSteps.checkResponseTimeStep(response, TestConstants.CRITICAL_RESPONSE_TIME);
        AssertionSteps.checkServerTypeStep(response, "cloudflare");
        AssertionSteps.checkContentEncodingStep(response, "gzip");
    }
}
