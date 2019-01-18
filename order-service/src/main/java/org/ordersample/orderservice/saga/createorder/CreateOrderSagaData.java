package org.ordersample.orderservice.saga.createorder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateOrderSagaData {

	private String id; 
    private String customerId;
    private String invoiceId;

	public CreateOrderSagaData(String id, String customerId, String invoiceId) {
		this.id = id;
		this.customerId = customerId;
		this.invoiceId = invoiceId;
	}

}
