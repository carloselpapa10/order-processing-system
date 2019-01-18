package org.ordersample.orderviewservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Document(collection="Invoices")
@Setter
@Getter
@NoArgsConstructor
public class Invoice{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	private String orderId;
	private String invoiceComment;

	public Invoice(String id, String orderId, String invoiceComment) {
		this.id = id;
		this.orderId = orderId;
		this.invoiceComment = invoiceComment;
	}
}
