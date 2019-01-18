package org.ordersample.customerservice.webapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateCustomerResponse {
	
	private String id;

	public CreateCustomerResponse(String id) {
		this.id = id;
	}
}
