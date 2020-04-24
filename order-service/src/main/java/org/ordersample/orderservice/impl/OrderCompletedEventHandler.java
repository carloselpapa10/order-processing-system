package org.ordersample.orderservice.impl;

import org.ordersample.domaininfo.order.api.events.OrderCompletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCompletedEventHandler implements ApplicationListener<OrderCompletedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(OrderCreatedEventHandler.class);

    @Override
    public void onApplicationEvent(OrderCompletedEvent orderCompletedEvent) {
        logger.info("orderCompletedEvent sisas");
    }
}
