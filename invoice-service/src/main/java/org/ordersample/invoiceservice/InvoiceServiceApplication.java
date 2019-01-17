package org.ordersample.invoiceservice;

import org.ordersample.invoiceservice.commandhandlers.InvoiceServiceCommandHandlersConfiguration;
import org.ordersample.invoiceservice.dao.InvoiceServiceConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import io.eventuate.jdbckafka.TramJdbcKafkaConfiguration;

@SpringBootApplication
@Import({InvoiceServiceConfiguration.class,
	InvoiceServiceCommandHandlersConfiguration.class,
	TramJdbcKafkaConfiguration.class})
public class InvoiceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoiceServiceApplication.class, args);
	}

	@Bean
	public MeterRegistryCustomizer meterRegistryCustomizer(@Value("${spring.application.name}") String serviceName) {
		return registry -> registry.config().commonTags("service", serviceName);
	}
}

