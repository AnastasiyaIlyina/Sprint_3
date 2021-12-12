package com.example.utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class CourierAuthenticator {

    public Response loginCourier(String login, String password){
        String preprocessedLogin = login == null ? "null" : "\"" + login + "\"";
        String preprocessedPassword = password == null ? "null" : "\"" + password + "\"";
        String loginRequestBody = "{\"login\":" + preprocessedLogin + ","
                + "\"password\":" + preprocessedPassword + "}";

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(loginRequestBody)
                .when()
                .post("https://qa-scooter.praktikum-services.ru/api/v1/courier/login");
    }
}
