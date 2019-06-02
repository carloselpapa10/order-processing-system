package org.ordersample.orderservice;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.ordersample.orderservice.controller.OrderServiceController;
import org.ordersample.orderservice.dao.OrderService;
import org.ordersample.orderservice.exception.InvalidOrderIdException;
import org.ordersample.orderservice.model.Order;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.ordersample.orderservice.saga.OrderDetails.*;
import static org.ordersample.orderservice.saga.OrderDetails.INVOICE_ID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext
@AutoConfigureMessageVerifier
public class RestBase {

    @Before
    public void setup() throws InvalidOrderIdException {
        OrderService orderService = mock(OrderService.class);
        OrderServiceController orderServiceController = new OrderServiceController(orderService);

        when(orderService.findOrder("123456")).thenReturn(new Order("123456", ORDER_DESCRIPTION, "1010", INVOICE_ID));
        when(orderService.findOrder("321")).thenThrow(new InvalidOrderIdException(String.format("Order ID %s does not exist", "321")));

        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(orderServiceController);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }
}
