package com.example.utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class CourierOrderAcceptor extends ScooterBaseURL{
    private static final String ORDER_ACCEPTOR_URI = "api/v1/orders/accept/";

    public void acceptOrder(Integer trackId, Integer courierId){
        given()
                .spec(getBaseSpec())
                .queryParam("courierId", courierId)
                .when()
                .put(ORDER_ACCEPTOR_URI + trackId);
    }
}
