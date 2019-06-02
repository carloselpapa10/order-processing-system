package org.ordersample.orderservice;

import io.eventuate.tram.commands.common.ChannelMapping;
import io.eventuate.tram.commands.common.DefaultChannelMapping;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.inmemory.TramInMemoryConfiguration;
import io.eventuate.tram.springcloudcontractsupport.EventuateContractVerifierConfiguration;
import org.junit.runner.RunWith;
import org.ordersample.domaininfo.order.api.events.OrderCreatedEvent;
import org.ordersample.domaininfo.order.api.info.OrderDTO;
import org.ordersample.orderservice.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import static java.util.Arrays.asList;
import static org.ordersample.orderservice.saga.OrderDetails.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessagingBase.TestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureMessageVerifier
public abstract class MessagingBase {

    @Configuration
    @EnableAutoConfiguration
    @Import({EventuateContractVerifierConfiguration.class, TramEventsPublisherConfiguration.class, TramInMemoryConfiguration.class})
    public static class TestConfiguration{

        @Bean
        public ChannelMapping channelMapping(){
            return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
        }
    }

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    protected void orderCreated(){
        domainEventPublisher.publish(Order.class, ORDER_ID, asList(new OrderCreatedEvent(new OrderDTO(ORDER_ID, ORDER_DESCRIPTION, CUSTOMER_ID, "123"))));
    }
}
