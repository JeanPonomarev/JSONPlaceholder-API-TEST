package com.typicode.jsonplaceholder.data_generator;

import com.github.javafaker.Faker;

public class RandomDataGenerator {
    Faker faker = new Faker();

    public String getLargeId() {
        return faker.regexify("[0-9]{100}");
    }

    public String getNormalRandomString() {
        return faker.regexify("[a-zA-Z]{10}");
    }

    public String getLargeRandomString() {
        return faker.regexify("[a-zA-Z]{300}");
    }

    public int getIdFromRange(int minId, int maxId) {
        return faker.random().nextInt(minId, maxId);
    }

    public String getTitle() {
        return faker.name().title();
    }

    public String getSentence() {
        return faker.lorem().sentence();
    }
}
