package com.example.utils;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;


public class CourierRegistrar extends ScooterBaseURL {

    private static final String COURIER_URI = "api/v1/courier";

    public ArrayList<String> registerRandomCredentialsCourier() {
        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);

        ArrayList<String> loginPass = new ArrayList<>();
        Response response = registerNewCourier(courierLogin, courierPassword, courierFirstName);

        if (response.statusCode() == 201) {
            loginPass.add(courierLogin);
            loginPass.add(courierPassword);
        }

        return loginPass;
    }

    public Response registerNewCourier(String login, String password, String firstName){
        String preprocessedLogin = login == null ? "null" : "\"" + login + "\"";
        String preprocessedPassword = password == null ? "null" : "\"" + password + "\"";
        String preprocessedFirstName = firstName == null ? "null" : "\"" + firstName + "\"";


        String registerRequestBody = "{\"login\":" + preprocessedLogin + ","
                + "\"password\":" + preprocessedPassword + ","
                + "\"firstName\":" + preprocessedFirstName + "}";

        return given()
                .spec(getBaseSpec())
                .body(registerRequestBody)
                .when()
                .post(COURIER_URI);
    }
}
