package com.example;

import com.example.utils.CourierRegistrar;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CourierRegistrationNegativeTest {
    private CourierRegistrar courierRegistrar;
    private final String login = "FatDog";
    private final String password = "123456Dog";
    private final String firstName = "Doggy";

    @Before
    public void beforeTests() {
        courierRegistrar = new CourierRegistrar();
    }

    @Test
    public void CreateCourierWithoutLoginResponseNotEnoughCredentials() {
        Response response = courierRegistrar.registerNewCourier(null, password, firstName);
        response.then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    public void CreateCourierWithoutPasswordResponseNotEnoughCredentials() {
        Response response = courierRegistrar.registerNewCourier(login, null, firstName);
        response.then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }
}



