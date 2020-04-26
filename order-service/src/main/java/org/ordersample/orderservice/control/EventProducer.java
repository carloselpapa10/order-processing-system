package org.ordersample.orderservice.control;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.subject.TopicRecordNameStrategy;
import org.apache.avro.generic.GenericRecord;
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

    private Producer<String, GenericRecord> producer;

    @PostConstruct
    private void init() {
        final Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.57:9092");
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 0);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());

        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, UUID.randomUUID().toString());
        props.put("value.subject.name.strategy", TopicRecordNameStrategy.class.getName());
        props.setProperty(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://127.0.0.1:8081");

        producer = new KafkaProducer<>(props);
        producer.initTransactions();
    }

    public void publish(String topic, String id, GenericRecord... events) {
        try {
            producer.beginTransaction();
            send(topic, id, events);
            producer.commitTransaction();
        } catch (ProducerFencedException e) {
            producer.close();
        } catch (KafkaException e) {
            producer.abortTransaction();
        }
    }

    private void send(String topic, String id, GenericRecord... events) {
        for (final GenericRecord event : events) {
            final ProducerRecord<String, GenericRecord> record = new ProducerRecord<>(topic, id, event);

            record.headers().add("transitId", UUID.randomUUID().toString().getBytes());
            logger.info("publishing = " + record);
            producer.send(record);
        }
    }

    @PreDestroy
    public void close() {
        producer.close();
    }
}
