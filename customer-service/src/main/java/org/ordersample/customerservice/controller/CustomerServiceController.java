package org.ordersample.customerservice.controller;

import org.ordersample.customerservice.dao.CustomerService;
import org.ordersample.customerservice.model.*;
import org.ordersample.customerservice.webapi.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("customer")
public class CustomerServiceController {

	private static final Logger log = LoggerFactory.getLogger(CustomerServiceController.class);

	@Autowired
	private CustomerService customerService;

	@PostMapping
	public CreateCustomerResponse createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest){
		log.info("CustomerService - CustomerServiceController - createCustomer");
		
		if(customerService.findCustomer(createCustomerRequest.getId()) == null) {
			Customer customer = customerService.createCustomer(new Customer(createCustomerRequest.getId(), createCustomerRequest.getName()));
			return new CreateCustomerResponse(customer.getId());
		}
		
		return null;
	}
			
	@GetMapping("{customerId}")
	public Customer findCustomer(@PathVariable("customerId") String id){
		log.info("CustomerService - CustomerServiceController - findCustomer");
		return customerService.findCustomer(id);
	} 			

	@PutMapping
	public ResponseEntity<Customer> updateCustomer(@RequestBody UpdateCustomerRequest customer){
		log.info("CustomerService - CustomerServiceController - updateCustomer");

		if(customerService.findCustomer(customer.getId()) != null) {
			return ResponseEntity.ok(customerService.updateCustomer(new Customer(customer.getId(), customer.getName())));
		}		
		return null;
	}
 			
	@DeleteMapping("/{customerId}")
	public String deleteCustomer(@PathVariable("customerId") String id){
		log.info("CustomerService - CustomerServiceController - updateCustomer");

		Customer customer = customerService.findCustomer(id);
		if(customer != null) {
			customerService.deleteCustomer(customer);
			return "Customer is being deleted...";
		}
		return "CustomerID does not exist!";
	} 
			
	@GetMapping
	public List<Customer> findAllCustomers(){
		log.info("CustomerService - CustomerServiceController - findAllCustomers");
		return customerService.findAll();
	}

}


