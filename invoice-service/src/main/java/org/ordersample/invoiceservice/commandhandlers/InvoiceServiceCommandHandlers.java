package org.ordersample.invoiceservice.commandhandlers;

import org.ordersample.domaininfo.common.Channels;
import org.ordersample.domaininfo.invoice.api.commands.*;
import org.ordersample.domaininfo.invoice.api.info.InvoiceDTO;
import org.ordersample.invoiceservice.dao.InvoiceService;
import org.ordersample.invoiceservice.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

public class InvoiceServiceCommandHandlers {

	private static final Logger log = LoggerFactory.getLogger(InvoiceServiceCommandHandlers.class);

	@Autowired
	private InvoiceService invoiceService;

	public CommandHandlers commandHandlers() {
		return SagaCommandHandlersBuilder
				.fromChannel(Channels.INVOICESERVICE)
				.onMessage(RequestInvoiceCommand.class, this::handleRequestInvoiceCommand)
				.onMessage(CompensateInvoiceCommand.class, this::handleCompensateInvoiceCommand)
				.onMessage(ValidateInvoiceByOrderService.class, this::handleValidateInvoiceByOrderService)
				.build();
	}		
		
	private Message handleRequestInvoiceCommand(CommandMessage<RequestInvoiceCommand> cm) {
		log.info("InvoiceService - InvoiceServiceCommandHandlers - handleRequestInvoiceCommand");
		
		RequestInvoiceCommand command = cm.getCommand();
		
		try {
			Invoice invoice = invoiceService.createInvoice(new Invoice(command.getInvoiceDTO().getOrderId(), command.getInvoiceDTO().getInvoiceComment()));
			return withSuccess(new InvoiceDTO(invoice.getId(), invoice.getOrderId(), invoice.getInvoiceComment()));
		}catch (Exception e) {
			return withFailure();
		}
		
	}

	private Message handleCompensateInvoiceCommand(CommandMessage<CompensateInvoiceCommand> cm) {
		log.info("InvoiceService - InvoiceServiceCommandHandlers - handleCompensateInvoiceCommand");
		
		CompensateInvoiceCommand command = cm.getCommand();
		
		try {
			invoiceService.rejectInvoice(new Invoice(command.getInvoiceDTO().getOrderId(), command.getInvoiceDTO().getInvoiceComment()));
			return withSuccess();			
		}catch (Exception e) {
			return withFailure();
		}
		
	}

	private Message handleValidateInvoiceByOrderService(CommandMessage<ValidateInvoiceByOrderService> cm) {
		log.info("InvoiceService - InvoiceServiceCommandHandlers - handleValidateInvoiceByOrderService");
		
		ValidateInvoiceByOrderService command = cm.getCommand();

		return invoiceService.findInvoice(command.getInvoiceDTO().getInvoiceId()) != null ? withSuccess() : withFailure();
	}

}
