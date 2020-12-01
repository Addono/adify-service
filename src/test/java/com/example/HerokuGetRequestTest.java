package com.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.simple.parser.JSONParser;


public class HerokuGetRequestTest {

    @Test
    @Tag("slow")
    void requestIntegrationTest() throws IOException {
        // Arrange
        HerokuGetRequest heroku = new HerokuGetRequest("adify");

        // Act
        String response = heroku.get("?product=PRODUCT_ID");

        // Assert
        assertEquals( String.format("{\"%s\":\"Paper\"}", new FeatureToggleStub().featureA() ? "product-name" : "product"), response);
    }

    @Test
    @Tag("slow")
    void contractTestHeroku() throws ParseException, IOException {
        // Arrange
        HerokuGetRequest heroku = new HerokuGetRequest("adify");

        // Act
        String response = heroku.get("?product=PRODUCT_ID");

        // Assert
        JSONObject obj = (JSONObject) new JSONParser().parse(response);
        assertTrue(obj.containsKey(new FeatureToggleStub().featureA() ? "product-name" : "product"));
    }

}
