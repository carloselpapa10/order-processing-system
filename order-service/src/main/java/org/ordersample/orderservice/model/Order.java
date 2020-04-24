package org.ordersample.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ordersample.domaininfo.order.api.info.OrderDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    private String id;
    private String description;
    private String customerId;
    private String invoiceId;
    private OrderState state;

    public void create(String id, String description, String customerId){
        state = OrderState.CREATED;
        this.id = id;
        this.description = description;
        this.customerId = customerId;
    }

    public enum OrderState {
        CREATED,
        ACCEPTED,
        STARTED,
        FINISHED,
        DELIVERED,
        CANCELLED
    }

}
