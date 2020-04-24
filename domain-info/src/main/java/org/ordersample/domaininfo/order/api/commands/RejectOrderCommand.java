package org.ordersample.domaininfo.order.api.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ordersample.domaininfo.order.api.info.OrderDTO;

@Setter
@Getter
@NoArgsConstructor
public class RejectOrderCommand {

    private OrderDTO orderDTO;

    public RejectOrderCommand(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }
}
