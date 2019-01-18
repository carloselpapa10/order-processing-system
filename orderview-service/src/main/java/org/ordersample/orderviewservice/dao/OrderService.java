package org.ordersample.orderviewservice.dao;

import java.util.List;
import org.ordersample.domaininfo.common.BusinessException;
import org.ordersample.orderviewservice.model.*;

public interface OrderService {

	public Order createOrder(Order order) throws BusinessException;				
	public Order findOrder(String id) throws BusinessException;			
	public void updateOrder(Order order) throws BusinessException;			
	public void rejectOrder(String id) throws BusinessException;			
	public void completeOrder(Order order) throws BusinessException;			
	public void editOrder(Order order) throws BusinessException;			
	public List<Order> findAll() throws BusinessException;
	public void updateInvoiceOrder(Order order, Invoice invoice) throws BusinessException;
	public void deleteOrder(Order order) throws BusinessException;
}	
