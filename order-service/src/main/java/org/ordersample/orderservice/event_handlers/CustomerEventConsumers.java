package org.ordersample.orderservice.event_handlers;

import org.ordersample.domaininfo.customer.api.events.CustomerCreatedEvent;
import org.ordersample.orderservice.eventhandling.EventHandler;
import org.ordersample.orderservice.eventhandling.ListenEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@ListenEvent("customerEvents")
public class CustomerEventConsumers {

    private static final Logger logger = LoggerFactory.getLogger(CustomerEventConsumers.class);

    @EventHandler
    public void on(CustomerCreatedEvent customerCreatedEvent) {
        logger.info("CustomerEventConsumers CUSTOMER_CREATED");
    }
}
