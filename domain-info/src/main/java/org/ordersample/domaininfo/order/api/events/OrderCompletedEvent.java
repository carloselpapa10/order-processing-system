package org.ordersample.domaininfo.order.api.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ordersample.domaininfo.order.api.info.OrderDTO;

@Setter
@Getter
@NoArgsConstructor
public class OrderCompletedEvent implements OrderDomainEvent{

	private OrderDTO orderDTO;

	public OrderCompletedEvent(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
	}
}
