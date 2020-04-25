package org.ordersample.orderservice.model;

import org.springframework.context.ApplicationEvent;


public class OrderEvent extends ApplicationEvent {


    public OrderEvent(Object source) {
        super(source);
    }
}
