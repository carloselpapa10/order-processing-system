package org.ordersample.domaininfo.customer.api.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ordersample.domaininfo.customer.api.info.CustomerDTO;

@Setter
@Getter
@NoArgsConstructor
public class CustomerUpdatedEvent implements CustomerDomainEvent{

	private CustomerDTO customerDTO;

	public CustomerUpdatedEvent(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}
}
