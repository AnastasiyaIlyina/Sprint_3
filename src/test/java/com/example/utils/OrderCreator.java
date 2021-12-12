package com.example.utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class OrderCreator {
    public Response createNewOrder(String firstName, String lastName, String address, String metroStation, String phone, Number rentTime, String deliveryDate, String comment, String[] colors) {

        String createOrderRequestBody = "{\"firstName\":\"" + firstName + "\","
                + "\"lastName\":\"" + lastName + "\","
                + "\"address\":\"" + address + "\","
                + "\"metroStation\":\"" + metroStation + "\","
                + "\"phone\":\"" + phone + "\","
                + "\"rentTime\":\"" + rentTime.toString() + "\","
                + "\"deliveryDate\":\"" + deliveryDate + "\","
                + "\"comment\":\"" + comment + "\","
                + "\"color\":[" + buildColorJsonString(colors) + "]}";

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(createOrderRequestBody)
                .when()
                .post("https://qa-scooter.praktikum-services.ru/api/v1/orders");
    }

    public String buildColorJsonString(String[] colors) {
        StringBuilder colorJson = new StringBuilder();
        for (int i = 0; i < colors.length; ++i) {
            if (i != 0) {
                colorJson.append(",");
            }
            colorJson.append("\"").append(colors[i]).append("\"");
        }
        return colorJson.toString();
    }
}

