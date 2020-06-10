package com.typicode.jsonplaceholder.test_cases.update_operations;

import com.typicode.jsonplaceholder.constants.TestConstants;
import com.typicode.jsonplaceholder.data_generator.RandomDataGenerator;
import com.typicode.jsonplaceholder.steps.AssertionSteps;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

@Epic(value = "Put request")
@Feature(value = "Update post")
@Story(value = "Update post with wrong data (negative scenario)")
@Owner(value = "Jean Ponomarev")
public class UpdatePostWithWrongDataTest {
    private static final RandomDataGenerator dataGenerator = new RandomDataGenerator();
    private static final Map<String, Object> requestBodyMap = new HashMap<>();

    @DataProvider
    private Object[] getURLPathId() {
        return new Object[]{
                TestConstants.NONEXISTENT_POST_ID, dataGenerator.getNormalRandomString(), dataGenerator.getLargeId()
        };
    }

    @Description(value = "Update post with wrong id in the URL")
    @Severity(value = SeverityLevel.CRITICAL)
    @Test(dataProvider = "getURLPathId")
    void testUpdatePostWithIncorrectPathId(Object id) {
        requestBodyMap.put("id", id);
        requestBodyMap.put("title", dataGenerator.getTitle());
        requestBodyMap.put("body", dataGenerator.getSentence());
        requestBodyMap.put("userId", TestConstants.VALID_USER_ID);

        Response response = RestAssured.given()
                .pathParam("id", id)
                .contentType("application/json; charset=utf-8")
                .body(requestBodyMap)
                .when()
                .put(TestConstants.BASE_URL_WITH_SLASH + "{id}");

        AssertionSteps.checkStatusCodeStep(response, 400, 404);
        AssertionSteps.checkStatusLineStep(response, "HTTP/1.1 400 Bad Request", "HTTP/1.1 404 Not Found");
        AssertionSteps.checkResponseTimeStep(response, TestConstants.CRITICAL_RESPONSE_TIME);
    }

    @DataProvider
    private Object[][] getRequestBodyData() {
        return new Object[][]{
                {TestConstants.NONEXISTENT_POST_ID, dataGenerator.getTitle(), dataGenerator.getSentence(), TestConstants.VALID_USER_ID},
                {dataGenerator.getNormalRandomString(), dataGenerator.getTitle(), dataGenerator.getSentence(), TestConstants.VALID_USER_ID},
                {dataGenerator.getLargeId(), dataGenerator.getTitle(), dataGenerator.getSentence(), TestConstants.VALID_USER_ID},
                {null, dataGenerator.getTitle(), dataGenerator.getSentence(), TestConstants.VALID_USER_ID},

                {TestConstants.VALID_POST_ID, null, dataGenerator.getSentence(), TestConstants.VALID_USER_ID},
                {TestConstants.VALID_POST_ID, "", dataGenerator.getSentence(), TestConstants.VALID_USER_ID},
                {TestConstants.VALID_POST_ID, dataGenerator.getLargeRandomString(), dataGenerator.getSentence(), TestConstants.VALID_USER_ID},

                {TestConstants.VALID_POST_ID, dataGenerator.getTitle(), null, TestConstants.VALID_USER_ID},
                {TestConstants.VALID_POST_ID, dataGenerator.getTitle(), "", TestConstants.VALID_USER_ID},

                {TestConstants.VALID_POST_ID, dataGenerator.getTitle(), dataGenerator.getSentence(), TestConstants.NONEXISTENT_USER_ID},
                {TestConstants.VALID_POST_ID, dataGenerator.getTitle(), dataGenerator.getSentence(), dataGenerator.getNormalRandomString()},
                {TestConstants.VALID_POST_ID, dataGenerator.getTitle(), dataGenerator.getSentence(), null},
                {TestConstants.VALID_POST_ID, dataGenerator.getTitle(), dataGenerator.getSentence(), dataGenerator.getLargeId()},
        };
    }

    @Description(value = "Update post with wrong body data")
    @Severity(value = SeverityLevel.CRITICAL)
    @Test(dataProvider = "getRequestBodyData")
    void testUpdatePostWithWrongBody(Object bodyId, String title, String body, Object userId) {
        requestBodyMap.put("id", bodyId);
        requestBodyMap.put("title", title);
        requestBodyMap.put("body", body);
        requestBodyMap.put("userId", userId);

        Response response = RestAssured.given()
                .pathParam("id", TestConstants.VALID_POST_ID)
                .contentType("application/json; charset=utf-8")
                .body(requestBodyMap)
                .when()
                .put(TestConstants.BASE_URL_WITH_SLASH + "{id}");

        AssertionSteps.checkStatusCodeStep(response, 400, 422);
        AssertionSteps.checkStatusLineStep(response, "HTTP/1.1 400 Bad Request", "HTTP/1.1 422 Unprocessable Entity");
        AssertionSteps.checkResponseTimeStep(response, TestConstants.CRITICAL_RESPONSE_TIME);
    }
}
