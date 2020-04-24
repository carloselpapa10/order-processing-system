package org.ordersample.orderservice.impl;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.ordersample.orderservice.control.EventConsumer;
import org.ordersample.orderservice.control.EventDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class OrderUpdatedConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderUpdatedConsumer.class);

    private EventConsumer eventConsumer;

//    @Inject
//    ExecutorService mes;

//    @Inject
//    Event<AppEvent> events;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @PostConstruct
    private void init() {
        final Properties kafkaProperties = new Properties();
        kafkaProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.57:9092");
        kafkaProperties.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        kafkaProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        kafkaProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        kafkaProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, EventDeserializer.class);

        //kafkaProperties.put("group.id", "order-consumer-" + UUID.randomUUID());
        kafkaProperties.put("group.id", "order-consumer-1");
        String orderTopic = "order";

        eventConsumer = new EventConsumer(kafkaProperties, ev -> {
            logger.info("firing = " + ev);
            applicationEventPublisher.publishEvent(ev);
            //events.fire(ev);
        }, orderTopic);

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.execute(eventConsumer);
//        mes.execute(eventConsumer);
    }

    @PreDestroy
    public void close() {
        eventConsumer.stop();
    }
}
