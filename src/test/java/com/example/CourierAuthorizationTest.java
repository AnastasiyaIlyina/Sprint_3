package com.example;

import com.example.utils.CourierAuthenticator;
import com.example.utils.CourierRegistrar;
import com.example.utils.CourierRemover;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierAuthorizationTest {
    private CourierRegistrar courierRegistrar;
    private CourierAuthenticator courierAuthenticator;
    private ArrayList<String> randomCourierLoginPass;

    @Before
    public void beforeTests() {
        courierRegistrar = new CourierRegistrar();
        courierAuthenticator = new CourierAuthenticator();
        randomCourierLoginPass = courierRegistrar.registerRandomCredentialsCourier();
    }


    @After
    public void afterTests() {
        var courierRemover = new CourierRemover();
        courierRemover.deleteCourier(randomCourierLoginPass.get(0), randomCourierLoginPass.get(1));
    }

    @Test
    public void LoginCourierResponseOk() {
        Response response = courierAuthenticator.loginCourier(randomCourierLoginPass.get(0), randomCourierLoginPass.get(1));
        response.then()
            .assertThat()
            .body("id", notNullValue())
            .and()
            .statusCode(200);
    }

    @Test
    public void LoginWithoutLoginCouriersReturnBadRequest() {
        Response response = courierAuthenticator.loginCourier(null, randomCourierLoginPass.get(1));
        response.then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    public void LoginWithoutPasswordCouriersReturnBadRequest() {
        Response response = courierAuthenticator.loginCourier(randomCourierLoginPass.get(0), null);
        response.then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    public void LoginWithWrongLoginCouriersReturnBadRequest() {
        Response response = courierAuthenticator.loginCourier("WrongLogin", randomCourierLoginPass.get(1));
        response.then()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    public void LoginWithWrongPasswordCouriersReturnBadRequest() {
        Response response = courierAuthenticator.loginCourier(randomCourierLoginPass.get(0), "WrongPass");
        response.then()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }


}



