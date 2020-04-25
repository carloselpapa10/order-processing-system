package org.ordersample.orderservice.control;

import com.example.protocol.orders.v1.OrderEvents;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Logger;

@Component
public class EventProducer {

    private static final Logger logger = Logger.getLogger(EventSerializer.class.getName());

    private Producer<String, OrderEvents.OrdersEnvelope> producer;
    private String topic;

    @PostConstruct
    private void init() {
        final Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.57:9092");
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 0);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, EventSerializer.class.getName());

        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, UUID.randomUUID().toString());
        //props.put("value.subject.name.strategy", TopicRecordNameStrategy.class.getName());
        props.setProperty(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://127.0.0.1:8081");

        producer = new KafkaProducer<>(props);
        topic = "order";
        producer.initTransactions();
    }

    public void publish(OrderEvents.OrdersEnvelope... events) {
        try {
            producer.beginTransaction();
            send(events);
            producer.commitTransaction();
        } catch (ProducerFencedException e) {
            producer.close();
        } catch (KafkaException e) {
            producer.abortTransaction();
        }
    }

    private void send(OrderEvents.OrdersEnvelope... events) {
        for (final OrderEvents.OrdersEnvelope event : events) {
            final ProducerRecord<String, OrderEvents.OrdersEnvelope> record = new ProducerRecord<>(topic, event.getCorrelationId(), event);

            record.headers().add("header-1", "something".getBytes());
            logger.info("publishing = " + record);
            producer.send(record);
        }
    }

    @PreDestroy
    public void close() {
        producer.close();
    }
}
