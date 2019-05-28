package org.ordersample.orderservice.controller;

import org.ordersample.orderservice.dao.OrderService;
import org.ordersample.orderservice.model.*;
import org.ordersample.orderservice.webapi.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/order")
public class OrderServiceController {

	private static final Logger log = LoggerFactory.getLogger(OrderServiceController.class);

	private OrderService orderService;

	public OrderServiceController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest createOrderRequest){
		log.info("OrderService - OrderServiceController - createOrder");
		
		Order order =orderService.createOrder(new Order(createOrderRequest.getDescription(), createOrderRequest.getCustomerId()));
		return new CreateOrderResponse(order.getId());
	}
			
	@GetMapping("/{orderId}")
	public Order findOrder(@PathVariable("orderId") String id){
		log.info("OrderService - OrderServiceController - findOrder");
		return orderService.findOrder(id);
	} 			

	@PutMapping
	public ResponseEntity<Order> updateOrder(@RequestBody Order order){
		log.info("OrderService - OrderServiceController - updateOrder");

		if(orderService.findOrder(order.getId()) != null) {
			order = orderService.updateOrder(order);
			return ResponseEntity.ok(order);
		}
		return null;
	}
	
	@DeleteMapping("/{orderId}")
	public String deleteOrder(@PathVariable("orderId") String id){
		log.info("OrderService - OrderServiceController - deleteOrder");
		
		if(orderService.findOrder(id) != null) {
			orderService.deleteOrder(orderService.findOrder(id));
			return "Order "+id+" is being deleted...";
		}
		return null;
		
	}
 			
	@GetMapping
	public List<Order> findAllOrders(){
		log.info("OrderService - OrderServiceController - findAllOrders");
		return orderService.findAll();
	}
}