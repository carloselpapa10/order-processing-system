package org.ordersample.orderservice.dao;

import java.util.List;
import org.ordersample.domaininfo.common.BusinessException;
import org.ordersample.orderservice.exception.InvalidOrderIdException;
import org.ordersample.orderservice.model.*;

public interface OrderService {

	public Order createOrder(Order order);
	public Order findOrder(String id) throws InvalidOrderIdException;
	public Order updateOrder(Order order);
	public void updateInvoiceOrder(String orderId, String invoiceId) throws InvalidOrderIdException;
	public void rejectOrder(Order order);
	public void completeOrder(Order order);
	public void editOrder(Order order);
	public List<Order> findAll();
	public void deleteOrder(Order order);
}		   
