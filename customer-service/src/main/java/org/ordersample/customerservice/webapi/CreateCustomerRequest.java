package org.ordersample.customerservice.webapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateCustomerRequest {

	private String id;
	private String name;

	public CreateCustomerRequest(String id, String name) {
		this.id = id;
		this.name = name;
	}
}
