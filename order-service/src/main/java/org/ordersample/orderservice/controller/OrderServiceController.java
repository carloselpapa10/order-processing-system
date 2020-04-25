package org.ordersample.orderservice.controller;

import com.example.protocol.orders.v1.OrderEvents;
import org.ordersample.domaininfo.order.api.info.OrderDTO;
import org.ordersample.orderservice.dao.OrderCommandService;
import org.ordersample.orderservice.webapi.CreateOrderRequest;
import org.ordersample.orderservice.webapi.CreateOrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderServiceController {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceController.class);

    private OrderCommandService orderCommandService;

    public OrderServiceController(OrderCommandService orderCommandService) {
        this.orderCommandService = orderCommandService;
    }

    @PostMapping
    public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest createOrderRequest) throws IOException {
        log.info("OrderService - OrderServiceController - createOrder");

        OrderDTO orderDTO = OrderDTO.builder()
                .id(UUID.randomUUID())
                .description(createOrderRequest.getDescription())
                .customerId(createOrderRequest.getCustomerId())
                .build();



        orderCommandService.createOrder(orderDTO);
        return new CreateOrderResponse(orderDTO.getId().toString());
    }

    @PutMapping
    public CreateOrderResponse completeOrder(@RequestBody CreateOrderRequest createOrderRequest) throws IOException {
        log.info("OrderService - OrderServiceController - createOrder");

        OrderDTO orderDTO = OrderDTO.builder()
                .id(UUID.randomUUID())
                .description(createOrderRequest.getDescription())
                .customerId(createOrderRequest.getCustomerId())
                .build();

        orderCommandService.completeOrder(orderDTO);
        return new CreateOrderResponse(orderDTO.getId().toString());
    }

//	@GetMapping("/{orderId}")
//	public Order findOrder(@PathVariable("orderId") String id) throws InvalidOrderIdException {
//		log.info("OrderService - OrderServiceController - findOrder");
//		return orderService.findOrder(id);
//	}
//
//	@PutMapping
//	public ResponseEntity<Order> updateOrder(@RequestBody Order order) throws InvalidOrderIdException{
//		log.info("OrderService - OrderServiceController - updateOrder");
//
//		if(orderService.findOrder(order.getId()) != null) {
//			order = orderService.updateOrder(order);
//			return ResponseEntity.ok(order);
//		}
//		return null;
//	}
//
//	@DeleteMapping("/{orderId}")
//	public String deleteOrder(@PathVariable("orderId") String id) throws InvalidOrderIdException{
//		log.info("OrderService - OrderServiceController - deleteOrder");
//
//		if(orderService.findOrder(id) != null) {
//			orderService.deleteOrder(orderService.findOrder(id));
//			return "Order "+id+" is being deleted...";
//		}
//		return null;
//
//	}
//
//	@GetMapping
//	public List<Order> findAllOrders(){
//		log.info("OrderService - OrderServiceController - findAllOrders");
//		return orderService.findAll();
//	}
}