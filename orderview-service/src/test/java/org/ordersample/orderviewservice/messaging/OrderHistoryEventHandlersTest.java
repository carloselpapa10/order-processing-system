package org.ordersample.orderviewservice.messaging;

import io.eventuate.tram.commands.common.ChannelMapping;
import io.eventuate.tram.commands.common.DefaultChannelMapping;
import io.eventuate.tram.commands.producer.TramCommandProducerConfiguration;
import io.eventuate.tram.inmemory.TramInMemoryConfiguration;
import io.eventuate.tram.springcloudcontractsupport.EventuateContractVerifierConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.ordersample.orderviewservice.dao.CustomerService;
import org.ordersample.orderviewservice.dao.InvoiceService;
import org.ordersample.orderviewservice.dao.OrderService;
import org.ordersample.orderviewservice.model.Customer;
import org.ordersample.orderviewservice.model.Invoice;
import org.ordersample.orderviewservice.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubFinder;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import static io.eventuate.util.test.async.Eventually.eventually;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderHistoryEventHandlersTest.TestConfiguration.class,
                            webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL,
                            ids = {"org.ordersample:order-service"})
@DirtiesContext
public class OrderHistoryEventHandlersTest {

    @Configuration
    @EnableAutoConfiguration
    @Import({EventHandlersConfiguration.class,
            TramCommandProducerConfiguration.class,
            TramInMemoryConfiguration.class, EventuateContractVerifierConfiguration.class})
    public static class TestConfiguration{

        @Bean
        public ChannelMapping channelMapping(){
            return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
        }

        @Bean
        public OrderService orderService(){
            return mock(OrderService.class);
        }

        @Bean
        public CustomerService customerService(){
            return mock(CustomerService.class);
        }

        @Bean
        public InvoiceService invoiceService(){
            return mock(InvoiceService.class);
        }
    }

    @Autowired
    private StubFinder stubFinder;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private InvoiceService invoiceService;

    @Test
    public void shouldHandleOrderCreatedEvent() throws InterruptedException{

        when(orderService.createOrder(any(Order.class))).thenReturn(new Order());
        when(customerService.findCustomer(any(String.class))).thenReturn(new Customer());

        stubFinder.trigger("orderCreatedEvent");

        eventually(() -> {
            ArgumentCaptor<Order> orderArg = ArgumentCaptor.forClass(Order.class);
            verify(orderService).createOrder(orderArg.capture());

            Order order = orderArg.getValue();

            assertEquals("111", order.getId());
        });
    }
}
