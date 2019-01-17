package org.ordersample.customerservice.commandhandlers;

import org.ordersample.customerservice.dao.CustomerService;
import org.ordersample.domaininfo.common.Channels;
import org.ordersample.domaininfo.customer.api.commands.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

public class CustomerServiceCommandHandlers {

	private static final Logger log = LoggerFactory.getLogger(CustomerServiceCommandHandlers.class);

	@Autowired
	private CustomerService customerService;

	public CommandHandlers commandHandlers() {
		return SagaCommandHandlersBuilder
				.fromChannel(Channels.CUSTOMERSERVICE)
				.onMessage(ValidateCustomerByOrderService.class, this::handleValidateCustomerByOrderService)
				.build();
	}		
		
	private Message handleValidateCustomerByOrderService(CommandMessage<ValidateCustomerByOrderService> cm) {
		log.info("CustomerService - CustomerServiceCommandHandlers - handleValidateCustomerByOrderService");

		ValidateCustomerByOrderService command = cm.getCommand();

		return customerService.findCustomer(command.getCustomerDTO().getId()) != null ? withSuccess() : withFailure();
	}


}
