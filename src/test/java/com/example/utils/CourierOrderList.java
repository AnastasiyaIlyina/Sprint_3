package com.example.utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class CourierOrderList extends ScooterBaseURL{
    private static final String COURIER_ORDER_LIST_URI = "api/v1/orders";

    public Response getOrderList(Integer courierId) {
        return given()
                .spec(getBaseSpec())
                .queryParam("courierId", courierId)
                .get(COURIER_ORDER_LIST_URI);
    }

}

