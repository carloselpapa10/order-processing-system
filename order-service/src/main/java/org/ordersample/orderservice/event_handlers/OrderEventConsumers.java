package org.ordersample.orderservice.event_handlers;

import com.example.protocol.orders.v1.OrderEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumers {
    private static final Logger logger = LoggerFactory.getLogger(OrderEventConsumers.class);

    public void orderEventHandler(OrderEvents.OrdersEnvelope ordersEnvelope){
        switch (ordersEnvelope.getPayloadCase()){
            case ORDER_CREATED_EVENT:
                logger.info("OrderEventConsumers ORDER_CREATED");
                break;
            case ORDER_UPDATED_EVENT:
                logger.info("OrderEventConsumers ORDER_UPDATED");
                break;
            case ORDER_COMPLETED_EVENT:
                logger.info("OrderEventConsumers ORDER_COMPLETED");
                break;
            case ORDER_DELETED_EVENT:
                logger.info("OrderEventConsumers ORDER_DELETED_EVENT");
                break;
            case PAYLOAD_NOT_SET:
                logger.info("OrderEventConsumers PAYLOAD_NOT_SET");
                break;
        }
    }

}
