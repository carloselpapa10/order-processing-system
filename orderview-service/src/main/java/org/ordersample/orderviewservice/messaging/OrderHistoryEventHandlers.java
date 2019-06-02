package org.ordersample.orderviewservice.messaging;

import org.ordersample.orderviewservice.dao.CustomerService;
import org.ordersample.orderviewservice.dao.InvoiceService;
import org.ordersample.orderviewservice.dao.OrderService;
import org.ordersample.orderviewservice.model.*;
import org.ordersample.domaininfo.order.api.events.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;	

public class OrderHistoryEventHandlers {
	
	private static final Logger log = LoggerFactory.getLogger(OrderHistoryEventHandlers.class);

	private OrderService orderService;
	private CustomerService customerService;
	private InvoiceService invoiceService;

	public OrderHistoryEventHandlers(OrderService orderService, CustomerService customerService, InvoiceService invoiceService) {
		this.orderService = orderService;
		this.customerService = customerService;
		this.invoiceService = invoiceService;
	}

	public DomainEventHandlers domainEventHandlers() {
		return DomainEventHandlersBuilder
				.forAggregateType("org.ordersample.orderservice.model.Order")
				.onEvent(OrderCompletedEvent.class, this::handleOrderCompletedEvent)
				.onEvent(OrderCreatedEvent.class, this::handleOrderCreatedEvent)
				.onEvent(OrderRejectedEvent.class, this::handleOrderRejectedEvent)
				.onEvent(OrderEditedEvent.class, this::handleOrderEditedEvent)
				.onEvent(OrderUpdatedInvoiceEvent.class, this::handlerOrderUpdatedInvoiceEvent)
				.onEvent(OrderDeletedEvent.class, this::handleOrderDeletedEvent)
				.build();
	}

	private void handleOrderCompletedEvent(DomainEventEnvelope<OrderCompletedEvent> dee) {
		log.info("OrderViewService - OrderHistoryEventHandlers - handleOrderCompletedEvent");

		Order order = orderService.findOrder(dee.getAggregateId());
		orderService.completeOrder(order);
	}

	private void handleOrderCreatedEvent(DomainEventEnvelope<OrderCreatedEvent> dee) {
		log.info("OrderViewService - OrderHistoryEventHandlers - handleOrderCreatedEvent");
		
		Customer customer = customerService.findCustomer(dee.getEvent().getOrderDTO().getCustomerId());
		orderService.createOrder(new Order(dee.getAggregateId(), dee.getEvent().getOrderDTO().getDescription(), customer));
	}

	private void handleOrderRejectedEvent(DomainEventEnvelope<OrderRejectedEvent> dee) {
		log.info("OrderViewService - OrderHistoryEventHandlers - handleOrderRejectedEvent");
		orderService.rejectOrder(dee.getAggregateId());
	}

	private void handleOrderEditedEvent(DomainEventEnvelope<OrderEditedEvent> dee) {
		log.info("OrderViewService - OrderHistoryEventHandlers - handleOrderEditedEvent");
		
		Customer customer = customerService.findCustomer(dee.getEvent().getOrderDTO().getCustomerId());
		Invoice invoice = invoiceService.findInvoice(dee.getEvent().getOrderDTO().getInvoiceId());
		Order order = new Order(dee.getAggregateId(), dee.getEvent().getOrderDTO().getDescription(), customer, invoice);
		
		orderService.editOrder(order);
	}
	
	private void handlerOrderUpdatedInvoiceEvent(DomainEventEnvelope<OrderUpdatedInvoiceEvent> dee) {
		log.info("OrderViewService - OrderHistoryEventHandlers - handlerOrderUpdatedInvoiceEvent");
		
		Invoice invoice = invoiceService.findInvoice(dee.getEvent().getOrderDTO().getInvoiceId());
		Order order = orderService.findOrder(dee.getAggregateId());
		
		orderService.updateInvoiceOrder(order, invoice);
	}
	
	private void handleOrderDeletedEvent(DomainEventEnvelope<OrderDeletedEvent> dee) {
		log.info("OrderViewService - OrderHistoryEventHandlers - handleOrderDeletedEvent");
		
		Order order = orderService.findOrder(dee.getAggregateId());
		orderService.deleteOrder(order);
	}

}
