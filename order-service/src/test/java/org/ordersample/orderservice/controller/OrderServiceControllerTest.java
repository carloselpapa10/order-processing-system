package org.ordersample.orderservice.controller;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.ordersample.orderservice.dao.OrderService;
import org.ordersample.orderservice.exception.InvalidOrderIdException;
import org.ordersample.orderservice.model.Order;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.Mockito.*;
import static org.ordersample.orderservice.saga.OrderDetails.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.http.HttpStatus.OK;

public class OrderServiceControllerTest {

    private OrderService orderService;
    private OrderServiceController orderServiceController;

    @Before
    public void setup() throws Exception{
        orderService = mock(OrderService.class);
        orderServiceController = new OrderServiceController(orderService);

        RestAssuredMockMvc.standaloneSetup(orderServiceController);
    }

    @Test
    public void shouldFindOrder() throws InvalidOrderIdException {
        when(orderService.findOrder(ORDER_ID)).thenReturn(new Order(ORDER_ID, ORDER_DESCRIPTION_2, CUSTOMER_ID, INVOICE_ID));

        given().
        when()
                .get("/order/"+ORDER_ID).
        then()
                .log().ifValidationFails()
                .statusCode(OK.value())
                .contentType(JSON)
                .body("id", equalTo(ORDER_ID))
                .body("customerId", equalTo(CUSTOMER_ID));
    }

    @Test
    public void shouldFindNotOrder() throws InvalidOrderIdException {
        when(orderService.findOrder("123")).thenThrow(new InvalidOrderIdException("Order ID "));

        given().
        when()
                .get("/order/123").
        then()
                .statusCode(400);
    }

}
