package org.ordersample.orderservice.order_control;

import com.example.protocol.orders.v1.OrderEvents;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static java.util.Arrays.asList;

public class OrderEventConsumer implements Runnable {

    private final KafkaConsumer<String, OrderEvents.OrdersEnvelope> consumer;
    private final Consumer<OrderEvents.OrdersEnvelope> eventConsumer;
    private final AtomicBoolean closed = new AtomicBoolean();

    public OrderEventConsumer(Properties kafkaProperties, Consumer<OrderEvents.OrdersEnvelope> eventConsumer, String... topics) {
        this.eventConsumer = eventConsumer;
        consumer = new KafkaConsumer<>(kafkaProperties);
        consumer.subscribe(asList(topics));
    }

    @Override
    public void run() {
        try {
            while (!closed.get()) {
                consume();
            }
        } catch (WakeupException e) {
            // will wakeup for closing
        } finally {
            consumer.close();
        }
    }

    private void consume() {
        ConsumerRecords<String, OrderEvents.OrdersEnvelope> records = consumer.poll(Duration.ofMillis(Long.MAX_VALUE));
        for (ConsumerRecord<String, OrderEvents.OrdersEnvelope> record : records) {
            eventConsumer.accept(record.value());
        }
        consumer.commitSync();
    }

    public void stop() {
        closed.set(true);
        consumer.wakeup();
    }
}
