package org.ordersample.orderservice.dao;

import java.util.List;
import org.ordersample.domaininfo.common.BusinessException;
import org.ordersample.orderservice.model.*;

public interface OrderService {

	public Order createOrder(Order order) throws BusinessException;				
	public Order findOrder(String id) throws BusinessException;			
	public Order updateOrder(Order order) throws BusinessException;
	public void updateInvoiceOrder(String orderId, String invoiceId) throws BusinessException;
	public void rejectOrder(Order order) throws BusinessException;			
	public void completeOrder(Order order) throws BusinessException;			
	public void editOrder(Order order) throws BusinessException;			
	public List<Order> findAll() throws BusinessException;
	public void deleteOrder(Order order) throws BusinessException;
}		   
