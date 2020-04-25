package org.ordersample.orderservice.event_handlers;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.ordersample.orderservice.order_control.OrderEventConsumer;
import org.ordersample.orderservice.order_control.OrderEventDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class OrderConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(OrderConsumerConfig.class);

    private OrderEventConsumer orderEventConsumer;

    @Autowired
    private OrderEventConsumers orderEventConsumers;

    @PostConstruct
    private void init() {
        final Properties kafkaProperties = new Properties();
        kafkaProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.57:9092");
        kafkaProperties.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        kafkaProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        kafkaProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        kafkaProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kafkaProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, OrderEventDeserializer.class.getName());
        kafkaProperties.put("schema.registry.url", "http://localhost:8081");

        kafkaProperties.put("group.id", "order-consumer-1");
        String orderTopic = "order";

        orderEventConsumer = new OrderEventConsumer(kafkaProperties, ev -> {
            logger.info("firing = " + ev);
            orderEventConsumers.orderEventHandler(ev);
        }, orderTopic);

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.execute(orderEventConsumer);
    }

    @PreDestroy
    public void close() {
        orderEventConsumer.stop();
    }
}
