package org.ordersample.domaininfo.order.api.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ordersample.domaininfo.order.api.info.OrderDTO;

import io.eventuate.tram.commands.common.Command;

@Setter
@Getter
@NoArgsConstructor
public class CompleteOrderCommand implements Command{
	
	private OrderDTO orderDTO;

	public CompleteOrderCommand(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
	}
}
