package org.ordersample.orderservice.event_handlers;

import io.example.ordersample.avro.schemas.OrderCompletedEvent;
import io.example.ordersample.avro.schemas.OrderCreatedEvent;
import org.ordersample.orderservice.eventhandling.EventHandler;
import org.ordersample.orderservice.eventhandling.ListenEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ListenEvent("orderEvents")
public class OrderEventConsumers {

    private static final Logger logger = LoggerFactory.getLogger(OrderEventConsumers.class);

    @EventHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        logger.info("OrderEventConsumers ORDER_CREATED");
    }

    @EventHandler
    public void on(OrderCompletedEvent orderCompletedEvent) {
        logger.info("OrderEventConsumers ORDER_COMPLETED");
    }

    public void something(){
        logger.info("Negativo");
    }
}
