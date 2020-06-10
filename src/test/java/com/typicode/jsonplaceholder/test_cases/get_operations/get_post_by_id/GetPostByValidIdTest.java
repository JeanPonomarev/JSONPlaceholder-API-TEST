package com.typicode.jsonplaceholder.test_cases.get_operations.get_post_by_id;

import com.typicode.jsonplaceholder.constants.TestConstants;
import com.typicode.jsonplaceholder.data_generator.RandomDataGenerator;
import com.typicode.jsonplaceholder.steps.AssertionSteps;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic(value = "Get request")
@Feature(value = "Get post by id")
@Story(value = "Get post by id, positive scenario")
@Owner(value = "Jean Ponomarev")
public class GetPostByValidIdTest {
    private Response response;
    private static final RandomDataGenerator dataGenerator = new RandomDataGenerator();

    private static final int VALID_POST_ID = dataGenerator.getIdFromRange(1, 100);

    @BeforeClass
    void getResponse() {
        response = RestAssured.given()
                .pathParam("id", VALID_POST_ID)
                .when()
                .get(TestConstants.BASE_URL_WITH_SLASH + "{id}");
    }

    @Description(value = "Get post by valid id")
    @Severity(value = SeverityLevel.BLOCKER)
    @Test
    public void testGetPostByValidId() {
        AssertionSteps.checkPostFieldsStep(response, VALID_POST_ID);

        AssertionSteps.checkStatusCodeStep(response, 200);
        AssertionSteps.checkStatusLineStep(response, "HTTP/1.1 200 OK");
        AssertionSteps.checkContentTypeStep(response, "application/json; charset=utf-8");
        AssertionSteps.checkResponseTimeStep(response, TestConstants.CRITICAL_RESPONSE_TIME);
        AssertionSteps.checkServerTypeStep(response, "cloudflare");
        AssertionSteps.checkContentEncodingStep(response, "gzip");
    }
}
