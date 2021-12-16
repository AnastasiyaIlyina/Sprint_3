package com.example;

import com.example.utils.OrderCreator;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderColorTest {
    private OrderCreator orderCreator;

    private final String firstName = "Кошка";
    private final String lastName = "Кошкеевна";
    private final String address = "ул. Мурчалкина";
    private final String metroStation = "Черкизовская";
    private final String phone = "+74564211212";
    private final Number rentTime = 1;
    private final String deliveryDate = "12.12.2021";
    private final String comment = "комментарий";
    private final String[] color;

    public CreateOrderColorTest(String[] color) {
        this.color = color;
        orderCreator = new OrderCreator();
    }

    @Parameterized.Parameters
    public static Object[][] getCreateOrderParameters() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}},
                {new String[]{"GREY"}}
        };
    }

    @Test
    public void orderCanBeCreateWithDifferentColors() {
        Response response = orderCreator.createNewOrder(firstName, lastName, address,metroStation ,phone, rentTime, deliveryDate, comment, color);

        int statusCode = response.statusCode();
        int orderTrack = response.path("track");

        assertThat("Status code is incorrect", statusCode, equalTo( 201));
        assertThat("Order track is empty", orderTrack, notNullValue());
    }
}
