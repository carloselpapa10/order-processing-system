package org.ordersample.orderservice.eventhandling;

import java.io.Serializable;

public interface Message<T> extends Serializable {
    String getIdentifier();

    T getPayload();

    Class<T> getPayloadType();
}
