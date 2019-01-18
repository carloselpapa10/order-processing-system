package org.ordersample.orderviewservice.dao;

import java.util.List;
import org.ordersample.domaininfo.common.BusinessException;
import org.ordersample.orderviewservice.model.*;

public interface CustomerService {

	public Customer createCustomer(Customer customer) throws BusinessException;				
	public Customer findCustomer(String id) throws BusinessException;			
	public void updateCustomer(Customer customer) throws BusinessException;			
	public void deleteCustomer(String id) throws BusinessException;			
	public List<Customer> findAll() throws BusinessException;

}	
