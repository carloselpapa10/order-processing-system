package org.ordersample.orderservice.dao;

import org.ordersample.domaininfo.order.api.info.OrderDTO;
import org.ordersample.orderservice.exception.InvalidOrderIdException;
import org.ordersample.orderservice.model.Order;

import java.io.IOException;
import java.util.List;

public interface OrderCommandService {

    void createOrder(OrderDTO orderDTO) throws IOException;

    Order findOrder(String id) throws InvalidOrderIdException;

    Order updateOrder(Order order);

    void updateInvoiceOrder(String orderId, String invoiceId) throws InvalidOrderIdException;

    void rejectOrder(Order order);

    void completeOrder(String orderId) throws IOException;

    void editOrder(Order order);

    List<Order> findAll();

    void deleteOrder(Order order);
}		   
