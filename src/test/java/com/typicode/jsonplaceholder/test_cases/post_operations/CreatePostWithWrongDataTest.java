package com.typicode.jsonplaceholder.test_cases.post_operations;

import com.typicode.jsonplaceholder.constants.TestConstants;
import com.typicode.jsonplaceholder.data_generator.RandomDataGenerator;
import com.typicode.jsonplaceholder.steps.AssertionSteps;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;

@Epic(value = "Post request")
@Feature(value = "Create a new post")
@Story(value = "Create post with wrong data (negative scenario)")
@Owner(value = "Jean Ponomarev")
public class CreatePostWithWrongDataTest {
    private static final RandomDataGenerator dataGenerator = new RandomDataGenerator();
    private static final Map<String, Object> requestBodyMap = new HashMap<>();

    @DataProvider
    private Object[][] getRequestBodyData() {
        return new Object[][]{
                {null, dataGenerator.getSentence(), dataGenerator.getIdFromRange(1, 10)},
                {"", dataGenerator.getSentence(), dataGenerator.getIdFromRange(1, 10)},
                {dataGenerator.getLargeRandomString(), dataGenerator.getSentence(), dataGenerator.getIdFromRange(1, 10)},
                {dataGenerator.getTitle(), null, dataGenerator.getIdFromRange(1, 10)},
                {dataGenerator.getTitle(), "", dataGenerator.getIdFromRange(1, 10)},
                {dataGenerator.getTitle(), dataGenerator.getSentence(), null},
                {dataGenerator.getTitle(), dataGenerator.getSentence(), dataGenerator.getNormalRandomString()},
                {dataGenerator.getTitle(), dataGenerator.getSentence(), TestConstants.NONEXISTENT_USER_ID}
        };
    }

    @Description(value = "Create post with one of the fields wrong")
    @Severity(value = SeverityLevel.CRITICAL)
    @Test(dataProvider = "getRequestBodyData")
    void testCreatePostWithWrongData(String title, String body, Object userId) {
        requestBodyMap.put("title", title);
        requestBodyMap.put("body", body);
        requestBodyMap.put("userId", userId);

        Response response = RestAssured.given()
                .contentType("application/json; charset=utf-8")
                .body(requestBodyMap)
                .when()
                .post(TestConstants.BASE_URL);

        AssertionSteps.checkStatusCodeStep(response, 422, 400);
        AssertionSteps.checkStatusLineStep(response, "HTTP/1.1 422 Unprocessable Entity", "HTTP/1.1 400 Bad Request");
        AssertionSteps.checkResponseTimeStep(response, TestConstants.CRITICAL_RESPONSE_TIME);
    }
}
