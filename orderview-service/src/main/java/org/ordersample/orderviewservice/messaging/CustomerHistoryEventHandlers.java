package org.ordersample.orderviewservice.messaging;

import org.ordersample.orderviewservice.dao.CustomerService;
import org.ordersample.orderviewservice.model.Customer;
import org.ordersample.domaininfo.customer.api.events.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;	


public class CustomerHistoryEventHandlers {
	
	private static final Logger log = LoggerFactory.getLogger(CustomerHistoryEventHandlers.class);

	private CustomerService customerService;

	public CustomerHistoryEventHandlers(CustomerService customerService) {
		this.customerService = customerService;
	}

	public DomainEventHandlers domainEventHandlers() {
		return DomainEventHandlersBuilder
				.forAggregateType("org.ordersample.customerservice.model.Customer")
				.onEvent(CustomerCreatedEvent.class, this::handleCustomerCreatedEvent)
				.onEvent(CustomerDeletedEvent.class, this::handleCustomerDeletedEvent)
				.onEvent(CustomerUpdatedEvent.class, this::handleCustomerUpdatedEvent)
				.build();
	}

	private void handleCustomerCreatedEvent(DomainEventEnvelope<CustomerCreatedEvent> dee) {
		log.info("OrderViewService - CustomerHistoryEventHandlers - handleCustomerCreatedEvent");
		
		Customer customer = new Customer(dee.getAggregateId(), dee.getEvent().getCustomerDTO().getName());
		customer = customerService.createCustomer(customer);
	}

	private void handleCustomerDeletedEvent(DomainEventEnvelope<CustomerDeletedEvent> dee) {
		log.info("OrderViewService - CustomerHistoryEventHandlers - handleCustomerCreatedEvent");

		customerService.deleteCustomer(dee.getAggregateId());
	}

	private void handleCustomerUpdatedEvent(DomainEventEnvelope<CustomerUpdatedEvent> dee) {
		log.info("OrderViewService - CustomerHistoryEventHandlers - handleCustomerUpdatedEvent");

		customerService.updateCustomer(new Customer(dee.getAggregateId(), dee.getEvent().getCustomerDTO().getName()));
	}

}
