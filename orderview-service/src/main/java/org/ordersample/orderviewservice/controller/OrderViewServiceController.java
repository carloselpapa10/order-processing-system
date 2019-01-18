package org.ordersample.orderviewservice.controller;

import org.ordersample.orderviewservice.dao.CustomerService;
import org.ordersample.orderviewservice.dao.InvoiceService;
import org.ordersample.orderviewservice.dao.OrderService;
import org.ordersample.orderviewservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("orderview")
public class OrderViewServiceController {

	private static final Logger log = LoggerFactory.getLogger(OrderViewServiceController.class);

	@Autowired
	private OrderService orderService;
				
	@Autowired
	private CustomerService customerService;
				
	@Autowired
	private InvoiceService invoiceService;
				
	@GetMapping("/order/{orderId}")
	public Order OrderViewService(@PathVariable("orderId") String id){
		log.info("OrderViewService - OrderViewServiceController - OrderViewService");

		return orderService.findOrder(id);
	}

	@GetMapping("/order/")
	public List<Order> findAllOrders(){
		log.info("OrderViewService - OrderViewServiceController - findAllOrders");

		return orderService.findAll();
	}

	@GetMapping("/customer/{customerId}")
	public Customer findCustomer(@PathVariable("customerId") String id){
		log.info("OrderViewService - OrderViewServiceController - findCustomer");

		return customerService.findCustomer(id);
	}

	@GetMapping("/customer/")
	public List<Customer> findAllCustomers(){
		log.info("OrderViewService - OrderViewServiceController - findAllCustomers");

		return customerService.findAll();
	}

	@GetMapping("/invoice/{invoiceId}")
	public Invoice findInvoice(@PathVariable("invoiceId") String id){
		log.info("OrderViewService - OrderViewServiceController - findInvoice");

		return invoiceService.findInvoice(id);
	}

	@GetMapping("/invoice/")
	public List<Invoice> findAllInvoices(){
		log.info("OrderViewService - OrderViewServiceController - findInvoice");

		return invoiceService.findAll();
	}

}
