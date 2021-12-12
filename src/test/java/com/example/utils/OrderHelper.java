package com.example.utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class OrderHelper {
    public Response getOrderId(Integer track) {
        return given()
                .header("Content-type", "application/json")
                .get("https://qa-scooter.praktikum-services.ru/api/v1/orders/track?t="+track.toString());
    }

}

