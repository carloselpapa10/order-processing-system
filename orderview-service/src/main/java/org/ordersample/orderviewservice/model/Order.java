package org.ordersample.orderviewservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Document(collection="Orders")
@Setter
@Getter
@NoArgsConstructor
public class Order{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	private String description;
	private Customer customer;
	private Invoice invoice;
	private boolean completed;

	public Order(String id) {
		this.id = id;
	}

	public Order(String id, String description, Customer customer) {
		this.id = id;
		this.description = description;
		this.customer = customer;
	}

	public Order(String id, String description, Customer customer, Invoice invoice) {
		this.id = id;
		this.description = description;
		this.customer = customer;
		this.invoice = invoice;
	}
}
