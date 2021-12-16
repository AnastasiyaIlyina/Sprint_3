package com.example;

import com.example.utils.CourierRegistrar;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CourierRegistrationFieldValidationTest {
    private CourierRegistrar courierRegistrar;
    private final String login = "FatDog";
    private final String password = "123456Dog";
    private final String firstName = "Doggy";

    @Before
    public void beforeTests() {
        courierRegistrar = new CourierRegistrar();
    }

    @Test
    public void courierWithoutLoginCannotBeRegistered() {
        Response response = courierRegistrar.registerNewCourier(null, password, firstName);
        int statusCode = response.statusCode();
        String errorMessage = response.then().extract().path("message");

        assertThat("Status code is incorrect", statusCode, equalTo( 400));
        assertThat("Error message is incorrect", errorMessage, equalTo( "Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void courierWithoutPasswordCannotBeRegistered() {
        Response response = courierRegistrar.registerNewCourier(login, null, firstName);
        int statusCode = response.statusCode();
        String errorMessage = response.then().extract().path("message");

        assertThat("Status code is incorrect", statusCode, equalTo( 400));
        assertThat("Error message is incorrect", errorMessage, equalTo( "Недостаточно данных для создания учетной записи"));
    }
}



