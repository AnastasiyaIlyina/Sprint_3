package com.example;

import com.example.utils.*;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;

public class OrderListTest {
    private CourierRegistrar courierRegistrar;
    private CourierAuthenticator courierAuthenticator;
    private ArrayList<String> randomCourierLoginPass;
    private OrderCreator orderCreator;
    private CourierOrderAcceptor courierOrderAcceptor;
    private CourierOrderList courierOrderList;
    private OrderHelper orderHelper;

    private final String firstName = "Кошка";
    private final String lastName = "Кошкеевна";
    private final String address = "ул. Мурчалкина";
    private final String metroStation = "Лубянка";
    private final String phone = "+74564211212";
    private final Number rentTime = 1;
    private final String deliveryDate = "12.12.2021";
    private final String comment = "комментарий";
    private final String[] color = {"BLACK"};

    @Before
    public void beforeTests() {
        courierRegistrar = new CourierRegistrar();
        courierAuthenticator = new CourierAuthenticator();
        randomCourierLoginPass = courierRegistrar.registerRandomCredentialsCourier();
        orderCreator = new OrderCreator();
        courierOrderAcceptor = new CourierOrderAcceptor();
        courierOrderList = new CourierOrderList();
        orderHelper = new OrderHelper();
    }

    @After
    public void afterTests() {
        var courierRemover = new CourierRemover();
        courierRemover.deleteCourier(randomCourierLoginPass.get(0), randomCourierLoginPass.get(1));
    }

    @Test
    public void LoginCourierResponseOk() {
        Response response = courierAuthenticator.loginCourier(randomCourierLoginPass.get(0), randomCourierLoginPass.get(1));
        Integer courierId = response.path("id");

        response = orderCreator.createNewOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        Integer track = response.path("track");

        response = orderHelper.getOrderId(track);
        Integer orderId = response.path("order.id");

        response = courierOrderAcceptor.acceptOrder(orderId, courierId);
        response.then()
                .assertThat()
                .body("ok", equalTo( true))
                .and()
                .statusCode(200);

        response = courierOrderList.getOrderList(courierId);
        response.then()
                .assertThat()
                .body("orders.size()", is(1))
                .and()
                .statusCode(200);
    }
}