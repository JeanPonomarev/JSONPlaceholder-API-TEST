package com.typicode.jsonplaceholder.steps;

import com.typicode.jsonplaceholder.entities.Post;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class AssertionSteps {
    @Step("Verification that retrieved list of posts has size equals to general amount of posts in database")
    public static void checkPostsListLengthStep(List<Post> postsList, int expectedPostsListLength) {
        assertThat(postsList, hasSize(expectedPostsListLength));
    }

    @Step("Verification that retrieved list of posts is sorted by id")
    public static void checkPostsListIsSortedByIdStep(List<Post> postsList) {
        int previousId = postsList.get(0).getId();
        int currentId;

        for (int i = 1; i < postsList.size(); i++) {
            currentId = postsList.get(i).getId();
            assertThat(currentId, greaterThan(previousId));
            previousId = currentId;
        }
    }

    @Step("Verification that that retrieved list of posts doesn't have null fields")
    public static void checkPostsListObjectHasOnlyNotNullFieldsStep(List<Post> postsList) {
        postsList.forEach(post -> {
            assertThat(post.getUserId(), notNullValue());
            assertThat(post.getId(), notNullValue());
            assertThat(post.getTitle(), notNullValue());
            assertThat(post.getBody(), notNullValue());
        });
    }

    @Step("Verification that retrieved post has correct fields")
    public static void checkPostFieldsStep(Response response, int expectedPostId) {
        response.then().assertThat()
                .body("id", equalTo(expectedPostId))
                .body("userId", notNullValue())
                .body("title", notNullValue())
                .body("body", notNullValue());
    }

    @Step("Verification that retrieved posts have correct userId field: \"{expectedUserId}\"")
    public static void checkPostsListHasCorrectUserIdStep(List<Post> postsList, int expectedUserId) {
        postsList.forEach(post -> {
            assertThat(post.getUserId(), equalTo(expectedUserId));
            assertThat(post.getId(), notNullValue());
            assertThat(post.getTitle(), notNullValue());
            assertThat(post.getBody(), notNullValue());
        });
    }

    @Step("Verification that created post has correct fields")
    public static void checkCreatedPostIsCorrectStep(Response response, Post post) {
        // логика создания поста на тренировочном API утсроена таким образом, что любой новый пост будет иметь id 101
        response.then().assertThat()
                .body("id", equalTo(101))
                .body("userId", equalTo(post.getUserId()))
                .body("title", equalTo(post.getTitle()))
                .body("body", equalTo(post.getBody()));
    }

    @Step("Verification that updated post has correct fields")
    public static void checkUpdatedPostIsCorrectStep(Response response, Post post) {
        response.then().assertThat()
                .body("id", equalTo(post.getId()))
                .body("userId", equalTo(post.getUserId()))
                .body("title", equalTo(post.getTitle()))
                .body("body", equalTo(post.getBody()));
    }

    @Step("Verification that actual status code equals to expected \"{expectedStatusCode}\"")
    public static void checkStatusCodeStep(Response response, int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }

    @Step("Verification that actual status code equals to expected \"{expectedStatusCode}\" or \"{alternativeExpectedStatusCode}\"")
    public static void checkStatusCodeStep(Response response, int expectedStatusCode, int alternativeExpectedStatusCode) {
        assertThat(response.statusCode(), oneOf(expectedStatusCode, alternativeExpectedStatusCode));
    }

    @Step("Verification that actual status line equals to expected \"{expectedStatusLine}\"")
    public static void checkStatusLineStep(Response response, String expectedStatusLine) {
        response.then().statusLine(expectedStatusLine);
    }

    @Step("Verification that actual status line equals to expected \"{expectedStatusLine}\" or \"{alternativeExpectedStatusLine}\"")
    public static void checkStatusLineStep(Response response, String expectedStatusLine, String alternativeExpectedStatusLine) {
        assertThat(response.getStatusLine(), oneOf(expectedStatusLine, alternativeExpectedStatusLine));
    }

    @Step("Verification that actual Content-Type header equals to expected \"{expectedContentType}\"")
    public static void checkContentTypeStep(Response response, String expectedContentType) {
        response.then().header("Content-Type", equalTo(expectedContentType));
    }

    @Step("Verification that actual response time isn't greater than critical response time \"{criticalResponseTime}\"")
    public static void checkResponseTimeStep(Response response, long criticalResponseTime) {
        response.then().time(lessThan(criticalResponseTime));
    }

    @Step("Verification that actual Server header equals to expected \"{serverType}\"")
    public static void checkServerTypeStep(Response response, String serverType) {
        response.then().assertThat().header("Server", equalTo(serverType));
    }

    @Step("Verification that Content-Encoding header equals to expected \"{contentEncoding}\"")
    public static void checkContentEncodingStep(Response response, String contentEncoding) {
        response.then().assertThat().header("Content-Encoding", equalTo(contentEncoding));
    }
}
