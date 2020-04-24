package org.ordersample.orderservice.impl;

import org.ordersample.domaininfo.order.api.events.OrderCreatedEvent;
import org.ordersample.orderservice.model.Order;
import org.ordersample.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Component
public class OrderCreatedEventHandler implements ApplicationListener<OrderCreatedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(OrderCreatedEventHandler.class);

    @Autowired
    private OrderRepository orderRepository;

    private Map<UUID, Order> orders = new ConcurrentHashMap<>();

    @Override
    public void onApplicationEvent(OrderCreatedEvent event) {
        Order order = Order.builder()
                .id(event.getOrderDTO().getId().toString())
                .description(event.getOrderDTO().getDescription())
                .build();

        orders.putIfAbsent(event.getOrderDTO().getId(), new Order());
//        orderRepository.save(order);
        applyFor(event.getOrderDTO().getId(),
                o -> o.create(event.getOrderDTO().getId().toString(),
                        event.getOrderDTO().getDescription(),
                        event.getOrderDTO().getCustomerId()));
    }

    private void applyFor(final UUID orderId, final Consumer<Order> consumer) {
        //final Optional<Order> order = orderRepository.findById(orderId.toString());
        final Order order = orders.get(orderId);
        if (order != null) {
            consumer.accept(order);
        }
    }

}
