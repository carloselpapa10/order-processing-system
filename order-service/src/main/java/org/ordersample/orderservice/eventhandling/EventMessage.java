package org.ordersample.orderservice.eventhandling;

import java.time.Instant;
import java.util.Map;

public interface EventMessage<T> extends Message<T> {
    String getIdentifier();

    Instant getTimestamp();

    EventMessage<T> withMetaData(Map<String, ?> var1);

    EventMessage<T> andMetaData(Map<String, ?> var1);
}
