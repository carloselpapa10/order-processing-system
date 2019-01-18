package org.ordersample.orderviewservice.messaging;

import org.ordersample.orderviewservice.dao.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ordersample.orderviewservice.impl.InvoiceServiceImpl;
import org.ordersample.orderviewservice.model.Invoice;
import org.ordersample.domaininfo.invoice.api.events.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;	

@Component
public class InvoiceHistoryEventHandlers {
	
	private static final Logger log = LoggerFactory.getLogger(InvoiceHistoryEventHandlers.class);

	@Autowired
	private InvoiceService invoiceService;
	
	public DomainEventHandlers domainEventHandlers() {
		return DomainEventHandlersBuilder
				.forAggregateType("org.ordersample.invoiceservice.model.Invoice")
				.onEvent(InvoiceCreatedEvent.class, this::handleInvoiceCreatedEvent)
				.onEvent(InvoiceCreationFailedEvent.class, this::handleInvoiceCreationFailedEvent)
				.build();
	}

	private void handleInvoiceCreatedEvent(DomainEventEnvelope<InvoiceCreatedEvent> dee) {
		log.info("OrderViewService - InvoiceHistoryEventHandlers - handleInvoiceCreatedEvent");

		invoiceService.createInvoice(new Invoice(dee.getAggregateId(), dee.getEvent().getInvoiceDTO().getOrderId(), dee.getEvent().getInvoiceDTO().getInvoiceComment()));
	}

	private void handleInvoiceCreationFailedEvent(DomainEventEnvelope<InvoiceCreationFailedEvent> dee) {
		log.info("OrderViewService - InvoiceHistoryEventHandlers - handleInvoiceCreationFailedEvent");

		invoiceService.rejectInvoice(dee.getAggregateId());
	}

}
