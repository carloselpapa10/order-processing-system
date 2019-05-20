package org.ordersample.customerservice.webapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UpdateCustomerRequest {

    private String id;
    private String name;

    public UpdateCustomerRequest(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
