package org.ordersample.orderservice.event_handlers;

import com.example.protocol.orders.v1.OrderEvents;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.ordersample.orderservice.eventhandling.EventHandler;
import org.ordersample.orderservice.eventhandling.ProcessingGroup;
import org.ordersample.orderservice.order_control.OrderEventConsumer;
import org.ordersample.orderservice.order_control.OrderEventDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class OrderConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(OrderConsumerConfig.class);

    private OrderEventConsumer orderEventConsumer;

    //@Autowired
    //private OrderEventConsumers orderEventConsumers;
    @Autowired
    private ApplicationContext context;

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
            //orderEventConsumers.orderEventHandler(ev);
            try {
                test(ev);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }, orderTopic);

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.execute(orderEventConsumer);
    }

    public void test(OrderEvents.OrdersEnvelope ordersEnvelope) throws Throwable {
        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();

        OrderEventConsumers orderEventConsumers = new OrderEventConsumers();

        Collection<Object> collection = context.getBeansWithAnnotation(ProcessingGroup.class).values();

        for(Object obj : collection){
            Method[] methods = obj.getClass().getDeclaredMethods();

            for(Method method : methods){
                Annotation[] annotations = method.getDeclaredAnnotations();

                for(Annotation annotation : annotations){
                    if(annotation instanceof EventHandler){
                        EventHandler eventHandler = (EventHandler) annotation;
                        logger.info(eventHandler.payloadType().getCanonicalName());
                    }
                }

            }
        }

        MethodType mt = MethodType.methodType(void.class, OrderEvents.class);
        MethodHandle methodHandle = publicLookup.findVirtual(OrderEventConsumers.class, "on", mt);

        methodHandle.invokeExact(orderEventConsumers, ordersEnvelope);
    }

    @PreDestroy
    public void close() {
        orderEventConsumer.stop();
    }
}
