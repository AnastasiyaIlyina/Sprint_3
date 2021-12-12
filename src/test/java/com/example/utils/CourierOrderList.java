package com.example.utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class CourierOrderList {
    public Response getOrderList(Integer courierId) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get("https://qa-scooter.praktikum-services.ru/api/v1/orders?courierId=" + courierId.toString());
    }

}

