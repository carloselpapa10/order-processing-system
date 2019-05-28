package org.ordersample.orderservice.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Orders")
@Setter
@Getter
@NoArgsConstructor
public class Order{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)						
	private String id; 
    private String description;									
    private String customerId;
    private String invoiceId;
    private boolean completed;

	public Order(String description, String customerId) {
		this.description = description;
		this.customerId = customerId;
	}

	public Order(String description, String customerId, String invoiceId) {
		this.description = description;
		this.customerId = customerId;
		this.invoiceId = invoiceId;
	}

	public Order(String id, String description, String customerId, String invoiceId) {
		this.id = id;
		this.description = description;
		this.customerId = customerId;
		this.invoiceId = invoiceId;
	}
}
