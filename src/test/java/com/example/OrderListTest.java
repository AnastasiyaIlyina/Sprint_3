package com.example;

import com.example.utils.*;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
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
    public void getCourierOrderList() {
        Response response = courierAuthenticator.loginCourier(randomCourierLoginPass.get(0), randomCourierLoginPass.get(1));
        Integer courierId = response.then().extract().path("id");

        response = orderCreator.createNewOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        Integer track = response.then().extract().path("track");

        response = orderHelper.getOrderId(track);
        Integer orderId = response.then().extract().path("order.id");

        courierOrderAcceptor.acceptOrder(orderId, courierId);

        response = courierOrderList.getOrderList(courierId);
        int statusCodeOrderList = response.statusCode();
        Integer orderListSize = response.then().extract().path("orders.size()");

        assertThat("Status code is incorrect", statusCodeOrderList, equalTo( 200));
        assertThat("Order list size is incorrect", orderListSize, is(1));
    }
}