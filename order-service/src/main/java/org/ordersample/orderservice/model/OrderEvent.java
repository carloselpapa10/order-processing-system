package org.ordersample.orderservice.model;

import org.apache.avro.generic.GenericRecord;
import org.springframework.context.ApplicationEvent;

public abstract class OrderEvent extends ApplicationEvent implements GenericRecord{
    public OrderEvent(Object source) {
        super(source);
    }
}
