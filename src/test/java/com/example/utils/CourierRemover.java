package com.example.utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class CourierRemover extends ScooterBaseURL{

    private CourierAuthenticator courierAuthenticator;
    private static final String DELETE_URI = "api/v1/courier/";

    public void deleteCourier(String login, String password){
        courierAuthenticator = new CourierAuthenticator();
        Response response = courierAuthenticator.loginCourier(login, password);
        Integer courierId = response.then().extract().path("id");
        String deleteRequestBody = "{\"id\":" + courierId + "}";
        given()
                .spec(getBaseSpec())
                .body(deleteRequestBody)
                .when()
                .delete(DELETE_URI + courierId );
    }
}
