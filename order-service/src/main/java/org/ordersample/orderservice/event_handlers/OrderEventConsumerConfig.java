package org.ordersample.orderservice.event_handlers;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.reflect.ReflectData;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.ordersample.orderservice.control.EventConsumer;
import org.ordersample.orderservice.eventhandling.EventHandler;
import org.ordersample.orderservice.eventhandling.ListenEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class OrderEventConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(OrderEventConsumerConfig.class);

    private EventConsumer eventConsumer;

    @Autowired
    private OrderEventConsumers orderEventConsumers;

    @Autowired
    private ApplicationContext context;

    private static <T> T mapRecordToObject(GenericRecord record, T object) {
        Assert.notNull(record, "record must not be null");
        Assert.notNull(object, "object must not be null");
        final Schema schema = ReflectData.get().getSchema(object.getClass());

        Assert.isTrue(schema.getFields().equals(record.getSchema().getFields()), "Schema fields didn’t match");
        record.getSchema().getFields().forEach(d -> PropertyAccessorFactory.forDirectFieldAccess(object).setPropertyValue(d.name(), record.get(d.name()) == null ? record.get(d.name()) : record.get(d.name()).toString()));
        return object;
    }

    @PostConstruct
    private void init() {
        final Properties kafkaProperties = new Properties();
        kafkaProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.57:9092");
        kafkaProperties.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        kafkaProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        kafkaProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        kafkaProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kafkaProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        kafkaProperties.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        kafkaProperties.put("group.id", "order-consumer-1");
        String orderTopic = "orders";

        eventConsumer = new EventConsumer(kafkaProperties, ev -> {
            logger.info("firing = " + ev);
            invokeMethodHandler(ev);
        }, orderTopic);

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.execute(eventConsumer);
    }

    private void invokeMethodHandler(GenericRecord record) {
        Collection<Object> collection = context.getBeansWithAnnotation(ListenEvent.class).values();

        for (Object obj : collection) {
            Method[] methods = obj.getClass().getDeclaredMethods();

            for (Method method : methods) {
                if (method.getGenericParameterTypes().length != 1) {
                    continue;
                }

                Annotation[] annotations = method.getDeclaredAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof EventHandler && method.getGenericParameterTypes()[0].getTypeName().equals(record.getSchema().getFullName())) {
                        try {
                            method.invoke(obj, mapRecordToObject(record, Class.forName(record.getSchema().getFullName()).getConstructor().newInstance()));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
    }

    @PreDestroy
    public void close() {
        eventConsumer.stop();
    }
}
