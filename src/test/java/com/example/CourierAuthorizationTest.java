package com.example;

import com.example.utils.CourierAuthenticator;
import com.example.utils.CourierRegistrar;
import com.example.utils.CourierRemover;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
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
    public void courierCanLogIn() {
        Response response = courierAuthenticator.loginCourier(randomCourierLoginPass.get(0), randomCourierLoginPass.get(1));
        int statusCode = response.statusCode();
        var courierId= response.then().extract().body();

        assertThat("Status code is incorrect", statusCode, equalTo( 200));
        assertThat("Courier ID is not created", courierId, notNullValue());
    }

    @Test
    public void courierCannotLogInWithoutLogin() {
        Response response = courierAuthenticator.loginCourier(null, randomCourierLoginPass.get(1));
        int statusCode = response.statusCode();
        String errorMessage= response.then().extract().path("message");

        assertThat("Status code is incorrect", statusCode, equalTo( 400));
        assertThat("Error Message is incorrect", errorMessage, equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void courierCannotLogInWithoutPassword() {
        Response response = courierAuthenticator.loginCourier(randomCourierLoginPass.get(0), null);
        int statusCode = response.statusCode();
        assertThat("Status code is incorrect", statusCode, equalTo( 400));

        String errorMessage = response.then().extract().path("message");
        assertThat("Error Message is incorrect", errorMessage, equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void courierCannotLogInWithWrongLogin() {
        Response response = courierAuthenticator.loginCourier("WrongLogin", randomCourierLoginPass.get(1));
        int statusCode = response.statusCode();
        String errorMessage= response.then().extract().path("message");

        assertThat("Status code is incorrect", statusCode, equalTo( 404));
        assertThat("Error Message is incorrect", errorMessage, equalTo("Учетная запись не найдена"));
    }

    @Test
    public void courierCannotLogInWithWrongPassword() {
        Response response = courierAuthenticator.loginCourier(randomCourierLoginPass.get(0), "WrongPass");
        int statusCode = response.statusCode();
        String errorMessage= response.then().extract().path("message");

        assertThat("Status code is incorrect", statusCode, equalTo( 404));
        assertThat("Error Message is incorrect", errorMessage, equalTo("Учетная запись не найдена"));
    }
}