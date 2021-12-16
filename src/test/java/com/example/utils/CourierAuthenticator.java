package com.example.utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class CourierAuthenticator extends ScooterBaseURL {

    private static final String LOGIN_URI = "/api/v1/courier/login";

    public Response loginCourier(String login, String password){
        String preprocessedLogin = login == null ? "null" : "\"" + login + "\"";
        String preprocessedPassword = password == null ? "null" : "\"" + password + "\"";
        String loginRequestBody = "{\"login\":" + preprocessedLogin + ","
                + "\"password\":" + preprocessedPassword + "}";

        return given()
                .spec(getBaseSpec())
                .body(loginRequestBody)
                .when()
                .post(LOGIN_URI);
    }
}
