package org.ordersample.orderservice.impl;

import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.micrometer.core.instrument.MeterRegistry;
import org.ordersample.orderservice.exception.InvalidOrderIdException;
import org.ordersample.orderservice.repository.OrderRepository;
import org.ordersample.domaininfo.order.api.events.*;
import org.ordersample.domaininfo.order.api.info.*;
import org.ordersample.orderservice.dao.OrderService;
import org.ordersample.orderservice.model.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.Optional;
import org.ordersample.orderservice.saga.createorder.CreateOrderSagaData;
import org.ordersample.orderservice.saga.updateorder.UpdateOrderSagaData;
import io.eventuate.tram.sagas.orchestration.SagaManager;

@Component
@Transactional
public class OrderServiceImpl implements OrderService{

	private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

	private OrderRepository orderRepository;
	private SagaManager<CreateOrderSagaData> createOrderSagaManager;
	private SagaManager<UpdateOrderSagaData> updateOrderSagaManager;
	private DomainEventPublisher domainEventPublisher;
	private Optional<MeterRegistry> meterRegistry;

	public OrderServiceImpl(OrderRepository orderRepository,
								SagaManager<CreateOrderSagaData> createOrderSagaManager,
									SagaManager<UpdateOrderSagaData> updateOrderSagaManager,
										DomainEventPublisher domainEventPublisher,
											Optional<MeterRegistry> meterRegistry) {
		this.orderRepository = orderRepository;
		this.createOrderSagaManager = createOrderSagaManager;
		this.updateOrderSagaManager = updateOrderSagaManager;
		this.domainEventPublisher = domainEventPublisher;
		this.meterRegistry = meterRegistry;
	}

	@Override
	public Order createOrder(Order order){
		log.info("OrderService - OrderServiceImpl - createOrder");
		
		order = orderRepository.save(order);
		domainEventPublisher.publish(Order.class, order.getId(), asList(new OrderCreatedEvent(new OrderDTO(order.getId(), order.getDescription(), order.getCustomerId(), order.getInvoiceId()))));

		CreateOrderSagaData data = new CreateOrderSagaData(order.getId(), order.getCustomerId(), order.getInvoiceId());
		createOrderSagaManager.create(data, Order.class, order.getId());

		meterRegistry.ifPresent(mr -> mr.counter("created_orders").increment());
		
		return order;
	}
				
	@Override
	public Order findOrder(String id) throws InvalidOrderIdException {
		log.info("OrderService - OrderServiceImpl - findOrder");

		Optional<Order> orderOptional = orderRepository.findById(id);

		if(orderOptional.isPresent()){
			return orderOptional.get();
		}

		throw new InvalidOrderIdException(String.format("Order ID %s does not exist!", id));
	}
			
	@Override
	public Order updateOrder(Order order){
		log.info("OrderService - OrderServiceImpl - updateOrder");

		order = orderRepository.save(order);
		
		UpdateOrderSagaData data = new UpdateOrderSagaData(order.getId(), order.getDescription(), order.getCustomerId(), order.getInvoiceId());
		updateOrderSagaManager.create(data);

		meterRegistry.ifPresent(mr -> mr.counter("updated_orders").increment());

		return order;
	}
			
	@Override
	public void rejectOrder(Order order){
		log.info("OrderService - OrderServiceImpl - rejectOrder");
		
		order.setCompleted(false);

		order = orderRepository.save(order);
		domainEventPublisher.publish(Order.class, order.getId(), asList(new OrderRejectedEvent(new OrderDTO(order.getId()))));
	}
			
	@Override
	public void completeOrder(Order order){
		log.info("OrderService - OrderServiceImpl - completeOrder");

		order.setCompleted(true);
		order = orderRepository.save(order);

		meterRegistry.ifPresent(mr -> mr.counter("completed_orders").increment());

		domainEventPublisher.publish(Order.class, order.getId(), asList(new OrderCompletedEvent(new OrderDTO(order.getId()))));
	}
			
	@Override
	public void editOrder(Order order){
		log.info("OrderService - OrderServiceImpl - editOrder");
		
		order = orderRepository.save(order);
		domainEventPublisher.publish(Order.class, order.getId(), asList(new OrderEditedEvent(new OrderDTO(order.getId(), order.getDescription(), order.getCustomerId(), order.getInvoiceId()))));

		meterRegistry.ifPresent(mr -> mr.counter("edited_orders").increment());
	}
			
	@Override
	public List<Order> findAll(){
		log.info("OrderService - OrderServiceImpl - findAll");
		return orderRepository.findAll();
	}

	@Override
	public void updateInvoiceOrder(String orderId, String invoiceId) throws InvalidOrderIdException{
		log.info("OrderService - OrderServiceImpl - updateInvoiceOrder");
		
		Order order = findOrder(orderId);
		order.setInvoiceId(invoiceId);
		order = orderRepository.save(order);

		domainEventPublisher.publish(Order.class, order.getId(), asList(new OrderUpdatedInvoiceEvent(new OrderDTO(order.getId(), order.getDescription(), order.getCustomerId(), order.getInvoiceId()))));

		meterRegistry.ifPresent(mr -> mr.counter("updatedInvoice_orders").increment());
	}

	@Override
	public void deleteOrder(Order order) {
		log.info("OrderService - OrderServiceImpl - deleteOrder");

		orderRepository.delete(order);
		domainEventPublisher.publish(Order.class, order.getId(), asList(new OrderDeletedEvent(new OrderDTO(order.getId()))));

		meterRegistry.ifPresent(mr -> mr.counter("deleted_orders").increment());
	}
	
}
