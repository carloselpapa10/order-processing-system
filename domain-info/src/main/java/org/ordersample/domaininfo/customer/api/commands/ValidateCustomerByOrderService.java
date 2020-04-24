package org.ordersample.domaininfo.customer.api.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ordersample.domaininfo.customer.api.info.CustomerDTO;

@Setter
@Getter
@NoArgsConstructor
public class ValidateCustomerByOrderService {

    private CustomerDTO customerDTO;

    public ValidateCustomerByOrderService(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }
}
