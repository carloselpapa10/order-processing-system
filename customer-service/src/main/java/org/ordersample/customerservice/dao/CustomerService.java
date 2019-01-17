package org.ordersample.customerservice.dao;

import java.util.List;
import org.ordersample.domaininfo.common.BusinessException;
import org.ordersample.customerservice.model.*;

public interface CustomerService {

	public Customer createCustomer(Customer customer) throws BusinessException;				
	public Customer findCustomer(String id) throws BusinessException;			
	public Customer updateCustomer(Customer customer) throws BusinessException;
	public void deleteCustomer(Customer customer) throws BusinessException;			
	public List<Customer> findAll() throws BusinessException;
}		   
