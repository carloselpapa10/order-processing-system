package org.ordersample.orderservice.eventhandling;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@MessageHandler(
        messageType = EventMessage.class
)
public @interface EventHandler {
    Class<?> payloadType();
}
