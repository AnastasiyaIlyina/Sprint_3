package com.example;

import com.example.utils.CourierRegistrar;
import com.example.utils.CourierRemover;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class CourierRegistrationTest {
    private CourierRegistrar courierRegistrar;

    private final String login = "BigFatKitty";
    private final String password = "123456Cat";
    private final String firstName = "Kitty";

    @Before
    public void beforeTests() {
        courierRegistrar = new CourierRegistrar();
    }

    @After
    public void afterTests() {
        var courierRemover = new CourierRemover();
        courierRemover.deleteCourier(login, password);
    }

    @Test
    public void courierCanBeRegistered() {
        Response response = courierRegistrar.registerNewCourier(login, password, firstName);

        int statusCode = response.statusCode();
        assertThat("Status code is incorrect", statusCode, equalTo( 201));

        boolean isCourierCreated = response.then().extract().path("ok");
        assertTrue("Courier is not registered", isCourierCreated);
    }

    @Test
    public void duplicateCourierCannotBeRegistered() {
        Response response = courierRegistrar.registerNewCourier(login, password, firstName);

        int statusCodeFirstCourier = response.statusCode();
        assertThat("Status code is incorrect", statusCodeFirstCourier, equalTo( 201));

        boolean isCourierCreatedFirstCourier = response.then().extract().path("ok");
        assertTrue("Courier is not registered", isCourierCreatedFirstCourier);

        response = courierRegistrar.registerNewCourier(login, password, firstName);
        int statusCodeDuplicateCourier = response.statusCode();
        assertThat("Status code is incorrect", statusCodeDuplicateCourier, equalTo( 409));

        String errorMessage = response.path("message");
        assertThat("Error message is incorrect", errorMessage, equalTo( "Этот логин уже используется"));
    }
}
