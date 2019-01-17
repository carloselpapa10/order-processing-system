package org.ordersample.domaininfo.order.api.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ordersample.domaininfo.order.api.info.OrderDTO;

@Setter
@Getter
@NoArgsConstructor
public class OrderDeletedEvent implements OrderDomainEvent{

	private OrderDTO orderDTO;

	public OrderDeletedEvent(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
	}
}
