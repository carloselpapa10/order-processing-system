package org.ordersample.orderservice.control;

import com.example.protocol.orders.v1.OrderEvents;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.logging.Logger;

public class EventSerializer extends Adapter implements Serializer<OrderEvents.OrdersEnvelope> {

    private static final Logger logger = Logger.getLogger(EventSerializer.class.getName());

    @Override
    public byte[] serialize(String s, OrderEvents.OrdersEnvelope ordersEnvelope) {
        return new byte[0];
    }

    @Override
    public byte[] serialize(String topic, Headers headers, OrderEvents.OrdersEnvelope data) {
        return data.toByteArray();
    }
}
