package org.ordersample.orderservice.webapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateOrderRequest {

	private String description;
	private String customerId;

	public CreateOrderRequest(String description, String customerId) {
		this.description = description;
		this.customerId = customerId;
	}
}
