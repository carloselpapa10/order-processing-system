package org.ordersample.domaininfo.customer.api.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ordersample.domaininfo.customer.api.info.CustomerDTO;

import io.eventuate.tram.commands.common.Command;

@Setter
@Getter
@NoArgsConstructor
public class ValidateCustomerByOrderService implements Command{

	private CustomerDTO customerDTO;

	public ValidateCustomerByOrderService(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}
}
