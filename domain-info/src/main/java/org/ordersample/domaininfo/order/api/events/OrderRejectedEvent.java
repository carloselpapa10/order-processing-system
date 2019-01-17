package org.ordersample.domaininfo.order.api.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ordersample.domaininfo.order.api.info.OrderDTO;

@Setter
@Getter
@NoArgsConstructor
public class OrderRejectedEvent implements OrderDomainEvent{

	private OrderDTO orderDTO;

	public OrderRejectedEvent(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
	}
}
