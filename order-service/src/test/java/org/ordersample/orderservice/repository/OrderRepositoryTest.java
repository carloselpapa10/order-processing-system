package org.ordersample.orderservice.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ordersample.orderservice.InstanceTestClassListener;
import org.ordersample.orderservice.SpringInstanceTestClassRunner;
import org.ordersample.orderservice.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.ordersample.orderservice.saga.OrderDetails.*;

@RunWith(SpringInstanceTestClassRunner.class)
@SpringBootTest(classes = OrderJpaTestConfiguration.class)
public class OrderRepositoryTest implements InstanceTestClassListener {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public void beforeClassSetup() {
    }

    @Test
    public void shouldSaveAndLoadOrder(){
        String orderId = transactionTemplate.execute((ts) -> {
            Order order = new Order(ORDER_DESCRIPTION, CUSTOMER_ID, INVOICE_ID);
            orderRepository.save(order);
            return order.getId();
        });

        transactionTemplate.execute((ts) -> {
            Order order = orderRepository.findById(orderId).get();

            assertNotNull(order);
            assertEquals(ORDER_DESCRIPTION, order.getDescription());
            assertEquals(CUSTOMER_ID, order.getCustomerId());

            return null;
        });
    }

    @Override
    public void afterClassSetup() {
        orderRepository.deleteAll();
    }
}
