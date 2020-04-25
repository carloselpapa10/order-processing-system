package org.ordersample.orderservice.control;

import com.example.protocol.orders.v1.OrderEvents;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;
import java.util.logging.Logger;

public class EventDeserializer implements Deserializer<OrderEvents.OrdersEnvelope> {

    private static final Logger logger = Logger.getLogger(EventDeserializer.class.getName());

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public OrderEvents.OrdersEnvelope deserialize(String s, byte[] bytes) {
        return null;
    }

    @Override
    public OrderEvents.OrdersEnvelope deserialize(String topic, Headers headers, byte[] data) {
        try {
            return OrderEvents.OrdersEnvelope.parseFrom(data);
        } catch (final InvalidProtocolBufferException e) {
            logger.severe(String.format("Received unparseable message", e));
            throw new RuntimeException("Received unparseable message " + e.getMessage(), e);
        }
    }

    @Override
    public void close() {

    }
}
