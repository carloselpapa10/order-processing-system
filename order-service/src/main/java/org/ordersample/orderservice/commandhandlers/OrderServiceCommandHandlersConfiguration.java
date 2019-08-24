package org.ordersample.orderservice.commandhandlers;

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
public class OrderServiceCommandHandlersConfiguration {

	@Value("${service.dispatcher}")
	private String serviceDispatcher;

	@Bean
	public OrderServiceCommandHandlers orderServiceCommandHandlers() {
	    return new OrderServiceCommandHandlers();
	}

	@Bean
	public CommandDispatcher commandDispatcher(OrderServiceCommandHandlers orderServiceCommandHandlers) {
	    return new SagaCommandDispatcher(serviceDispatcher, orderServiceCommandHandlers.commandHandlers());
	}
}
