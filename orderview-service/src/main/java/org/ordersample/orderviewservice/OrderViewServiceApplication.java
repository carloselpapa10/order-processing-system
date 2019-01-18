package org.ordersample.orderviewservice;

import org.ordersample.orderviewservice.messaging.EventHandlersConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import io.eventuate.jdbckafka.TramJdbcKafkaConfiguration;

@SpringBootApplication
@Import({EventHandlersConfiguration.class, TramJdbcKafkaConfiguration.class})
public class OrderViewServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderViewServiceApplication.class, args);
	}
}

