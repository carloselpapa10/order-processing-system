package org.ordersample.customerservice;

import org.ordersample.customerservice.commandhandlers.CustomerServiceCommandHandlersConfiguration;
import org.ordersample.customerservice.dao.CustomerServiceConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import io.eventuate.jdbckafka.TramJdbcKafkaConfiguration;

@SpringBootApplication
@Import({CustomerServiceConfiguration.class,
	CustomerServiceCommandHandlersConfiguration.class,
	TramJdbcKafkaConfiguration.class})
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

	@Bean
	public MeterRegistryCustomizer meterRegistryCustomizer(@Value("${spring.application.name}") String serviceName) {
		return registry -> registry.config().commonTags("service", serviceName);
	}
}

