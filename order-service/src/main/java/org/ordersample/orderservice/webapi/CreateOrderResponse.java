package org.ordersample.orderservice.webapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateOrderResponse {
	
	private String id;

	public CreateOrderResponse(String id) {
		this.id = id;
	}
}
