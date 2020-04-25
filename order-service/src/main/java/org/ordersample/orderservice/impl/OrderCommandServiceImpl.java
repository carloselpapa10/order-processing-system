package org.ordersample.orderservice.impl;

import com.example.protocol.orders.v1.OrderEvents;
import org.ordersample.domaininfo.order.api.info.OrderDTO;
import org.ordersample.orderservice.dao.OrderCommandService;
import org.ordersample.orderservice.exception.InvalidOrderIdException;
import org.ordersample.orderservice.model.Order;
import org.ordersample.orderservice.order_control.OrderEventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@Transactional
public class OrderCommandServiceImpl implements OrderCommandService {

    private static final Logger log = LoggerFactory.getLogger(OrderCommandServiceImpl.class);
    private final String TOPIC = "order";

    @Autowired
    private OrderEventProducer orderEventProducer;

    @Override
    public void createOrder(OrderDTO orderDTO) {
        log.info("OrderService - OrderServiceImpl - createOrder");

        OrderEvents.OrdersEnvelope ordersEnvelope = OrderEvents.OrdersEnvelope.newBuilder()
                .setCorrelationId(UUID.randomUUID().toString())
                .setOrderCreatedEvent(OrderEvents.OrderCreatedEvent.newBuilder()
                        .setId(orderDTO.getId().toString())
                        .setDescription(orderDTO.getDescription())
                        .setCustomerId(orderDTO.getDescription())
                ).build();

        orderEventProducer.publish(TOPIC, orderDTO.getId().toString(), ordersEnvelope);
    }

    @Override
    public void completeOrder(OrderDTO orderDTO) throws IOException {
        log.info("OrderService - OrderServiceImpl - completeOrder");

        OrderEvents.OrdersEnvelope ordersEnvelope = OrderEvents.OrdersEnvelope.newBuilder()
                .setCorrelationId(UUID.randomUUID().toString())
                .setOrderCompletedEvent(OrderEvents.OrderCompletedEvent.newBuilder()
                        .setOrderId(orderDTO.getId().toString())
                ).build();

        orderEventProducer.publish(TOPIC, orderDTO.getId().toString(), ordersEnvelope);
    }

    @Override
    public Order findOrder(String id) throws InvalidOrderIdException {
        log.info("OrderService - OrderServiceImpl - findOrder");

        throw new InvalidOrderIdException(String.format("Order ID %s does not exist!", id));
    }

    @Override
    public Order updateOrder(Order order) {
        log.info("OrderService - OrderServiceImpl - updateOrder");

        return order;
    }

    @Override
    public void rejectOrder(Order order) {
        log.info("OrderService - OrderServiceImpl - rejectOrder");

    }

    @Override
    public void editOrder(Order order) {
        log.info("OrderService - OrderServiceImpl - editOrder");

    }

    @Override
    public List<Order> findAll() {
        log.info("OrderService - OrderServiceImpl - findAll");
        return null;
    }

    @Override
    public void updateInvoiceOrder(String orderId, String invoiceId) throws InvalidOrderIdException {
        log.info("OrderService - OrderServiceImpl - updateInvoiceOrder");

        Order order = findOrder(orderId);
        order.setInvoiceId(invoiceId);
    }

    @Override
    public void deleteOrder(Order order) {
        log.info("OrderService - OrderServiceImpl - deleteOrder");
    }
}
