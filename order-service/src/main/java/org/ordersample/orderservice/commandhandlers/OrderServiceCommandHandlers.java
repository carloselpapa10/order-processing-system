package org.ordersample.orderservice.commandhandlers;

import org.ordersample.domaininfo.common.Channels;
import org.ordersample.domaininfo.order.api.commands.*;
import org.ordersample.orderservice.dao.OrderService;
import org.ordersample.orderservice.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

public class OrderServiceCommandHandlers {

	private static final Logger log = LoggerFactory.getLogger(OrderServiceCommandHandlers.class);

	@Autowired
	private OrderService orderService;

	public CommandHandlers commandHandlers() {
		return SagaCommandHandlersBuilder
				.fromChannel(Channels.ORDERSERVICE)
				.onMessage(CompleteOrderCommand.class, this::handleCompleteOrderCommand)
				.onMessage(RejectOrderCommand.class, this::handleRejectOrderCommand)
				.onMessage(EditOrderCommand.class, this::handleEditOrderCommand)
				.build();
	}		
		
	private Message handleCompleteOrderCommand(CommandMessage<CompleteOrderCommand> cm) {
		log.info("OrderService - OrderServiceCommandHandlers - handleCompleteOrderCommand");
		
		CompleteOrderCommand command = cm.getCommand();
		Order order = orderService.findOrder(command.getOrderDTO().getId());

		if(order != null) {
			orderService.completeOrder(order);
			return withSuccess();
		}
		
		return withFailure();		
	}

	private Message handleRejectOrderCommand(CommandMessage<RejectOrderCommand> cm) {
		log.info("OrderService - OrderServiceCommandHandlers - handleRejectOrderCommand");
		
		RejectOrderCommand command = cm.getCommand();
		Order order = orderService.findOrder(command.getOrderDTO().getId());
		
		if(order != null) {
			orderService.rejectOrder(order);
			return withSuccess();
		}
		
		return withFailure();
	}

	private Message handleEditOrderCommand(CommandMessage<EditOrderCommand> cm) {
		log.info("OrderService - OrderServiceCommandHandlers - handleEditOrderCommand");
		
		EditOrderCommand command = cm.getCommand();
		Order order = orderService.findOrder(command.getOrderDTO().getId());
		
		if(order != null) {
			order.setDescription(command.getOrderDTO().getDescription());
			order.setCustomerId(command.getOrderDTO().getCustomerId());
			order.setInvoiceId(command.getOrderDTO().getInvoiceId());
			
			orderService.editOrder(order);
			return withSuccess();
		}
		
		return withFailure();
	}

}
