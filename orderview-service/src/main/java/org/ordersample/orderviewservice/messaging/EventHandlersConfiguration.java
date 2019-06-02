package org.ordersample.orderviewservice.messaging;

import org.ordersample.orderviewservice.dao.CustomerService;
import org.ordersample.orderviewservice.dao.InvoiceService;
import org.ordersample.orderviewservice.dao.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.messaging.consumer.MessageConsumer;

@Configuration
public class EventHandlersConfiguration {

	@Bean
	public OrderHistoryEventHandlers orderHistoryEventHandlers(OrderService orderService, CustomerService customerService, InvoiceService invoiceService){
		return new OrderHistoryEventHandlers(orderService, customerService, invoiceService);
	}

	@Bean
	public CustomerHistoryEventHandlers customerHistoryEventHandlers(CustomerService customerService){
		return new CustomerHistoryEventHandlers(customerService);
	}

	@Bean
	public InvoiceHistoryEventHandlers invoiceHistoryEventHandlers(InvoiceService invoiceService){
		return new InvoiceHistoryEventHandlers(invoiceService);
	}

	@Bean
	public DomainEventDispatcher orderHistoryDomainEventDispatcher(OrderHistoryEventHandlers orderHistoryEventHandlers, MessageConsumer messageConsumer) {
		return new DomainEventDispatcher("orderHistoryDomainEventDispatcher", orderHistoryEventHandlers.domainEventHandlers(), messageConsumer);
	}			

	@Bean
	public DomainEventDispatcher customerHistoryDomainEventDispatcher(CustomerHistoryEventHandlers customerHistoryEventHandlers, MessageConsumer messageConsumer) {
		return new DomainEventDispatcher("customerHistoryDomainEventDispatcher", customerHistoryEventHandlers.domainEventHandlers(), messageConsumer);
	}			

	@Bean
	public DomainEventDispatcher invoiceHistoryDomainEventDispatcher(InvoiceHistoryEventHandlers invoiceHistoryEventHandlers, MessageConsumer messageConsumer) {
		return new DomainEventDispatcher("invoiceHistoryDomainEventDispatcher", invoiceHistoryEventHandlers.domainEventHandlers(), messageConsumer);
	}			

}			
