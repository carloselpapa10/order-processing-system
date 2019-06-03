package org.ordersample.invoiceservice;

import io.eventuate.tram.commands.common.ChannelMapping;
import io.eventuate.tram.commands.common.DefaultChannelMapping;
import io.eventuate.tram.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.inmemory.TramInMemoryConfiguration;
import io.eventuate.tram.springcloudcontractsupport.EventuateContractVerifierConfiguration;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.ordersample.invoiceservice.commandhandlers.InvoiceServiceCommandHandlersConfiguration;
import org.ordersample.invoiceservice.dao.InvoiceService;
import org.ordersample.invoiceservice.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.context.annotation.*;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessagingBase.TestConfiguration.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureMessageVerifier
public abstract class MessagingBase {

    @Configuration
    @EnableAutoConfiguration
    @Import({InvoiceServiceCommandHandlersConfiguration.class, EventuateContractVerifierConfiguration.class
            , TramEventsPublisherConfiguration.class, TramInMemoryConfiguration.class
    })
    public static class TestConfiguration{

        @Bean
        public InvoiceService invoiceService(){
            return mock(InvoiceService.class);
        }

        @Bean
        public ChannelMapping channelMapping(){
            return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
        }
    }

    @Autowired
    private InvoiceService invoiceService;

    @Before
    public void setup(){
        reset(invoiceService);
        when(invoiceService.createInvoice(any(Invoice.class))).thenReturn(new Invoice("1", "111", "Invoice Comment"));
    }
}