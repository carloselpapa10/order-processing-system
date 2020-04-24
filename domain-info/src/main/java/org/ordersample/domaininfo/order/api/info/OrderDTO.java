package org.ordersample.domaininfo.order.api.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.JsonObject;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private UUID id;
    private String description;
    private String customerId;

    public OrderDTO(JsonObject jsonObject) {
        this(UUID.fromString(jsonObject.getString("id")),
                jsonObject.getString("description"),
                jsonObject.getString("customerId"));
    }
}
