package org.ordersample.invoiceservice.commandhandlers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaParticipantConfiguration;

@Configuration
@Import({ SagaParticipantConfiguration.class, TramEventsPublisherConfiguration.class })
public class InvoiceServiceCommandHandlersConfiguration {

	@Bean
	public InvoiceServiceCommandHandlers invoiceServiceCommandHandlers() {
	    return new InvoiceServiceCommandHandlers();
	}

	@Bean
	public CommandDispatcher commandDispatcher(InvoiceServiceCommandHandlers invoiceServiceCommandHandlers) {
	    return new SagaCommandDispatcher("invoiceServiceDispatcher", invoiceServiceCommandHandlers.commandHandlers());
	}
}
