package org.ordersample.customerservice.impl;

import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.micrometer.core.instrument.MeterRegistry;
import org.ordersample.customerservice.repository.CustomerRepository;
import org.ordersample.domaininfo.common.BusinessException;
import org.ordersample.domaininfo.customer.api.events.*;
import org.ordersample.domaininfo.customer.api.info.*;
import org.ordersample.customerservice.dao.CustomerService;
import org.ordersample.customerservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class CustomerServiceImpl implements CustomerService{

	private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

	private final CustomerRepository customerRepository;

	@Autowired
	private DomainEventPublisher domainEventPublisher;

	private Optional<MeterRegistry> meterRegistry;

	public CustomerServiceImpl(CustomerRepository customerRepository, Optional<MeterRegistry> meterRegistry) {
		this.customerRepository = customerRepository;
		this.meterRegistry = meterRegistry;
	}

	@Override
	public Customer createCustomer(Customer customer) throws BusinessException{
		log.info("CustomerService - CustomerServiceImpl - createCustomer");

		customer = customerRepository.save(customer);
		domainEventPublisher.publish(Customer.class, customer.getId(), asList(new CustomerCreatedEvent(new CustomerDTO(customer.getId(), customer.getName()))));

		meterRegistry.ifPresent(mr -> mr.counter("created_customers").increment());

		return customer;
	}
				
	@Override
	public Customer findCustomer(String id) throws BusinessException{
		log.info("CustomerService - CustomerServiceImpl - findCustomer");

		return customerRepository.existsById(id) ? customerRepository.findById(id).get() : null ;
	}
			
	@Override
	public Customer updateCustomer(Customer customer) throws BusinessException{
		log.info("CustomerService - CustomerServiceImpl - updateCustomer");

		customer = customerRepository.save(customer);
		domainEventPublisher.publish(Customer.class, customer.getId(), asList(new CustomerUpdatedEvent(new CustomerDTO(customer.getId(), customer.getName()))));

		meterRegistry.ifPresent(mr -> mr.counter("updated_customers").increment());

		return customer;
	}
			
	@Override
	public void deleteCustomer(Customer customer) throws BusinessException{
		log.info("CustomerService - CustomerServiceImpl - deleteCustomer");

		customerRepository.delete(customer);
		domainEventPublisher.publish(Customer.class, customer.getId(), asList((new CustomerDeletedEvent(new CustomerDTO(customer.getId())))));

		meterRegistry.ifPresent(mr -> mr.counter("deleted_customers").increment());
	}
			
	@Override
	public List<Customer> findAll() throws BusinessException{
		log.info("CustomerService - CustomerServiceImpl - createCustomer");

		return customerRepository.findAll();
	}
	
}
