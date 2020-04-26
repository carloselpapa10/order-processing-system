package org.ordersample.orderservice.webapi;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateOrderRequest {

    private String description;
    private String customerId;

    public CreateOrderRequest(String description, String customerId) {
        this.description = description;
        this.customerId = customerId;
    }
}
