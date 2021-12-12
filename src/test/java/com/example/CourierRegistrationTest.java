package com.example;

import com.example.utils.CourierRegistrar;
import com.example.utils.CourierRemover;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CourierRegistrationTest {
    private CourierRegistrar courierRegistrar;
    private final String login = "SmallestCat";
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
    public void CreateCourierResponseOk() {
        Response response = courierRegistrar.registerNewCourier(login, password, firstName);
        response.then()
            .assertThat()
            .body("ok", equalTo( true))
            .and()
            .statusCode(201);
    }

    @Test
    public void CreateIdenticalCouriersResponseAlreadyExist() {
        Response response = courierRegistrar.registerNewCourier(login, password, firstName);
        response.then()
                .assertThat()
                .body("ok", equalTo(true))
                .and()
                .statusCode(201);
        response = courierRegistrar.registerNewCourier(login, password, firstName);
        response.then()
                .assertThat()
                .body("message", equalTo("Этот логин уже используется"))
                .and()
                .statusCode(409);
    }
}



