package com.example.utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class CourierOrderAcceptor {

    public Response acceptOrder(Integer trackId, Integer courierId){

        return given()
                .header("Content-type", "application/json")
                .when()
                .put("https://qa-scooter.praktikum-services.ru/api/v1/orders/accept/"+ trackId.toString() + "?courierId=" + courierId.toString());
    }
}
