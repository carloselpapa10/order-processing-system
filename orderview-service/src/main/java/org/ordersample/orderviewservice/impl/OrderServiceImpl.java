package org.ordersample.orderviewservice.impl;

import org.ordersample.domaininfo.common.BusinessException;
import org.ordersample.orderviewservice.dao.OrderService;
import org.ordersample.orderviewservice.model.*;
import org.ordersample.orderviewservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Component
@Transactional
public class OrderServiceImpl implements OrderService{

	private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public Order createOrder(Order order) throws BusinessException{
		log.info("OrderViewService - OrderServiceImpl - createOrder");

		return orderRepository.save(order);
	}
				
	@Override
	public Order findOrder(String id) throws BusinessException{
		log.info("OrderViewService - OrderServiceImpl - findOrder");

		return orderRepository.existsById(id) ? orderRepository.findById(id).get() : null;
	}
			
	@Override
	public void updateOrder(Order order) throws BusinessException{
		log.info("OrderViewService - OrderServiceImpl - updateOrder");
	}
			
	@Override
	public void rejectOrder(String id) throws BusinessException{
		log.info("OrderViewService - OrderServiceImpl - rejectOrder");

		Order order = findOrder(id);
		orderRepository.delete(order);
	}
			
	@Override
	public void completeOrder(Order order) throws BusinessException{
		log.info("OrderViewService - OrderServiceImpl - completeOrder");

		order.setCompleted(true);
		orderRepository.save(order);
	}
			
	@Override
	public void editOrder(Order order) throws BusinessException{
		log.info("OrderViewService - OrderServiceImpl - editOrder");

		orderRepository.save(order);
	}
			
	@Override
	public List<Order> findAll() throws BusinessException{
		log.info("OrderViewService - OrderServiceImpl - findAll");

		return orderRepository.findAll();
	}

	@Override
	public void updateInvoiceOrder(Order order, Invoice invoice) throws BusinessException {
		log.info("OrderViewService - OrderServiceImpl - updateInvoiceOrder");

		order.setInvoice(invoice);
		orderRepository.save(order);
	}

	@Override
	public void deleteOrder(Order order) throws BusinessException {
		log.info("OrderViewService - OrderServiceImpl - deleteOrder");

		orderRepository.delete(order);
	}

}
