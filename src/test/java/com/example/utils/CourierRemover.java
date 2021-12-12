package com.example.utils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class CourierRemover {

    public void deleteCourier(String login, String password){
        String loginRequestBody = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + password + "\"}";

        Integer id = given()
                .header("Content-type", "application/json")
                .and()
                .body(loginRequestBody)
                .when()
                .post("https://qa-scooter.praktikum-services.ru/api/v1/courier/login")
                .path("id");

        String deleteRequestBody = "{\"id\":" + id.toString() + "}";
        given()
                .header("Content-type", "application/json")
                .and()
                //.body(deleteRequestBody)
                .when()
                .delete("https://qa-scooter.praktikum-services.ru/api/v1/courier/" + id)
                .then()
                .assertThat()
                .body("ok", equalTo( true))
                .and()
                .statusCode(200);
    }
}
