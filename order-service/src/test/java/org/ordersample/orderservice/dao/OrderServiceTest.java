package org.ordersample.orderservice.dao;

import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.sagas.orchestration.SagaManager;
import org.junit.Before;
import org.junit.Test;
import org.ordersample.domaininfo.order.api.events.OrderCreatedEvent;
import org.ordersample.domaininfo.order.api.info.OrderDTO;
import org.ordersample.orderservice.impl.OrderServiceImpl;
import org.ordersample.orderservice.model.Order;
import org.ordersample.orderservice.repository.OrderRepository;
import org.ordersample.orderservice.saga.createorder.CreateOrderSagaData;
import org.ordersample.orderservice.saga.updateorder.UpdateOrderSagaData;

import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.mockito.Matchers.same;
import static org.ordersample.orderservice.saga.OrderDetails.*;
import static java.util.Arrays.asList;

public class OrderServiceTest {

    private OrderServiceImpl orderService;
    private OrderRepository orderRepository;
    private SagaManager<CreateOrderSagaData> createOrderSagaSagaManager;
    private SagaManager<UpdateOrderSagaData> updateOrderSagaSagaManager;
    private DomainEventPublisher domainEventPublisher;

    @Before
    public void setup(){
        orderRepository = mock(OrderRepository.class);
        createOrderSagaSagaManager = mock(SagaManager.class);
        updateOrderSagaSagaManager = mock(SagaManager.class);
        domainEventPublisher = mock(DomainEventPublisher.class);

        orderService = new OrderServiceImpl(orderRepository, createOrderSagaSagaManager, updateOrderSagaSagaManager, domainEventPublisher, Optional.empty());
    }

    @Test
    public void shouldCreateOrder(){

        when(orderRepository.save(any(Order.class))).then(invocation -> {
            Order order = (Order) invocation.getArguments()[0];
            order.setId(ORDER_ID);
            return order;
        });

        Order order = orderService.createOrder(new Order(ORDER_DESCRIPTION, CUSTOMER_ID, INVOICE_ID));

        /*Verify that OrderService saved the newly created Order in the database*/
        verify(orderRepository).save(same(order));

        /*Verify that OrderService published an Order-CreatedEvent*/
        verify(domainEventPublisher).publish(Order.class, ORDER_ID,
                asList(new OrderCreatedEvent(new OrderDTO(ORDER_ID, ORDER_DESCRIPTION, CUSTOMER_ID, INVOICE_ID))));

        /*Verify that OrderService created a CreateOrderSaga instance*/
        verify(createOrderSagaSagaManager)
                .create(new CreateOrderSagaData(ORDER_ID, CUSTOMER_ID, INVOICE_ID),
                        Order.class,
                            ORDER_ID);
    }

    @Test
    public void shouldUpdateOrder(){

        when(orderRepository.save(any(Order.class))).then(invocation -> {
            Order order = (Order) invocation.getArguments()[0];
            order.setId(ORDER_ID);
            return order;
        });

        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.of(new Order(ORDER_ID, ORDER_DESCRIPTION_2, CUSTOMER_ID, INVOICE_ID)));

        Order order = orderService.updateOrder(orderRepository.findById(ORDER_ID).get());

        /*Verify that OrderService saved the newly updated Order in the database*/
        verify(orderRepository).save(same(order));

        /*Verify that OrderService created an UpdateOrderSaga instance*/
        verify(updateOrderSagaSagaManager)
                .create(new UpdateOrderSagaData(ORDER_ID, ORDER_DESCRIPTION_2, CUSTOMER_ID, INVOICE_ID));
    }
}
