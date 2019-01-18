package org.ordersample.orderviewservice.impl;

import org.ordersample.domaininfo.common.BusinessException;
import org.ordersample.orderviewservice.dao.CustomerService;
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
public class CustomerServiceImpl implements CustomerService{

	private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Customer createCustomer(Customer customer) throws BusinessException{
		log.info("OrderViewService - CustomerServiceImpl - createCustomer");
		return customerRepository.save(customer);
	}
				
	@Override
	public Customer findCustomer(String id) throws BusinessException{
		log.info("OrderViewService - CustomerServiceImpl - findCustomer");
		return customerRepository.existsById(id) ? customerRepository.findById(id).get() : null;
	}
			
	@Override
	public void updateCustomer(Customer customer) throws BusinessException{
		log.info("OrderViewService - CustomerServiceImpl - updateCustomer");
		customerRepository.save(customer);
	}
			
	@Override
	public void deleteCustomer(String id) throws BusinessException{
		log.info("OrderViewService - CustomerServiceImpl - deleteCustomer");
		Customer customer = findCustomer(id);
		customerRepository.delete(customer);
	}
			
	@Override
	public List<Customer> findAll() throws BusinessException{
		log.info("OrderViewService - CustomerServiceImpl - findAll");
		return customerRepository.findAll();
	}

}
