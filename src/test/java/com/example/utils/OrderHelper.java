package com.example.utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class OrderHelper extends ScooterBaseURL{
    private static final String ORDER_ID_URI = "/api/v1/orders/track";

    public Response getOrderId(Integer track) {
        return given()
                .spec(getBaseSpec())
                .queryParam("t", track)
                .when()
                .get(ORDER_ID_URI);
    }

}

