package org.ordersample.orderservice.proxy;

import io.eventuate.tram.commands.common.ChannelMapping;
import io.eventuate.tram.commands.common.DefaultChannelMapping;
import io.eventuate.tram.commands.producer.TramCommandProducerConfiguration;
import io.eventuate.tram.inmemory.TramInMemoryConfiguration;
import io.eventuate.tram.sagas.orchestration.SagaCommandProducer;
import io.eventuate.tram.springcloudcontractsupport.EventuateContractVerifierConfiguration;
import io.eventuate.tram.springcloudcontractsupport.EventuateTramRoutesConfigurer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ordersample.domaininfo.invoice.api.commands.RequestInvoiceCommand;
import org.ordersample.domaininfo.invoice.api.info.InvoiceDTO;
import org.ordersample.orderservice.saga.createorder.CreateOrderSaga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.BatchStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InvoiceServiceProxyIntegrationTest.TestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(ids = {"org.ordersample:invoice-service"}, stubsMode = StubRunnerProperties.StubsMode.LOCAL)
@DirtiesContext
public class InvoiceServiceProxyIntegrationTest {

    @Configuration
    @EnableAutoConfiguration
    @Import({TramCommandProducerConfiguration.class, TramInMemoryConfiguration.class,
            EventuateContractVerifierConfiguration.class})
    public static class TestConfiguration{
        @Bean
        public ChannelMapping channelMapping(){
            return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
        }

        @Bean
        public DataSource dataSource(){
            EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
            return builder.setType(EmbeddedDatabaseType.H2)
                    .addScript("eventuate-tram-embedded-schema.sql")
                    .addScript("eventuate-tram-sagas-embedded.sql")
                    .build();
        }

        @Bean
        public EventuateTramRoutesConfigurer eventuateTramRoutesConfigurer(BatchStubRunner batchStubRunner){
            return new EventuateTramRoutesConfigurer(batchStubRunner);
        }

        @Bean
        public SagaMessagingTestHelper sagaMessagingTestHelper(){
            return new SagaMessagingTestHelper();
        }

        @Bean
        public SagaCommandProducer sagaCommandProducer(){
            return new SagaCommandProducer();
        }

        @Bean
        public InvoiceServiceProxy invoiceServiceProxy(){
            return new InvoiceServiceProxy();
        }
    }

    @Autowired
    private SagaMessagingTestHelper sagaMessagingTestHelper;

    @Autowired
    private InvoiceServiceProxy invoiceServiceProxy;

    @Test
    public void shouldSuccessfullyRequestInvoice(){
        RequestInvoiceCommand command = new RequestInvoiceCommand(new InvoiceDTO("1", "111", "Invoice Comment"));

        InvoiceDTO expectedReply = new InvoiceDTO("1", "111", "Invoice Comment");
        String sagaType = CreateOrderSaga.class.getName();

        InvoiceDTO reply = sagaMessagingTestHelper.sendAndReceiveCommand(invoiceServiceProxy.requestInvoiceCommand, command, InvoiceDTO.class, sagaType);

        assertEquals(expectedReply, reply);
    }
}
