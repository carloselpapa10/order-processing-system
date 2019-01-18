package org.ordersample.orderservice;

import org.ordersample.orderservice.commandhandlers.OrderServiceCommandHandlersConfiguration;
import org.ordersample.orderservice.dao.OrderServiceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import io.eventuate.jdbckafka.TramJdbcKafkaConfiguration;

@SpringBootApplication
@Import({OrderServiceConfiguration.class,
	OrderServiceCommandHandlersConfiguration.class,
	TramJdbcKafkaConfiguration.class})
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}
}

