package org.ordersample.customerservice.commandhandlers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaParticipantConfiguration;

@Configuration
@Import({ SagaParticipantConfiguration.class, TramEventsPublisherConfiguration.class })
public class CustomerServiceCommandHandlersConfiguration {

	@Value("${service.dispatcher}")
	private String serviceDispatcher;

	@Bean
	public CustomerServiceCommandHandlers customerServiceCommandHandlers() {
	    return new CustomerServiceCommandHandlers();
	}

	@Bean
	public CommandDispatcher commandDispatcher(CustomerServiceCommandHandlers customerServiceCommandHandlers) {
	    return new SagaCommandDispatcher(serviceDispatcher, customerServiceCommandHandlers.commandHandlers());
	}
}
