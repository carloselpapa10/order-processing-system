package org.ordersample.invoiceservice.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

	public Invoice(String orderId, String invoiceComment) {
		this.orderId = orderId;
		this.invoiceComment = invoiceComment;
	}
}
