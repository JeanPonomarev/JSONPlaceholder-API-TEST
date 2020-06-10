package com.typicode.jsonplaceholder.constants;

public class TestConstants {
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/posts";
    public static final String BASE_URL_WITH_SLASH = "https://jsonplaceholder.typicode.com/posts/";

    public static final int POSTS_AMOUNT = 100;
    public static final int USERS_POST_AMOUNT = 10;

    // большое время ответа выбрано вследстие медленной работы данного некоммерческого API
    public static final long CRITICAL_RESPONSE_TIME = 60000L;

    public static final int VALID_POST_ID = 1;
    public static final int NONEXISTENT_POST_ID = 0;

    public static final int VALID_USER_ID = 1;
    public static final int NONEXISTENT_USER_ID = 0;
}
