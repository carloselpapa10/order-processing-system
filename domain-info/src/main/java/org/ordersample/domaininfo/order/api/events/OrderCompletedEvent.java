package org.ordersample.domaininfo.order.api.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ordersample.domaininfo.AppEvent;
import org.ordersample.domaininfo.order.api.info.OrderDTO;

import javax.json.JsonObject;
import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
public class OrderCompletedEvent extends AppEvent {

	private OrderDTO orderDTO;

	public OrderCompletedEvent(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
	}

	public OrderCompletedEvent(Object source, OrderDTO orderDTO) {
		super(source);
		this.orderDTO = orderDTO;
	}

	public OrderCompletedEvent(Object source, OrderDTO orderDTO, Instant instant) {
		super(source, instant);
		this.orderDTO = orderDTO;
	}

	public OrderCompletedEvent(JsonObject jsonObject) {
		this(new OrderDTO(jsonObject.getJsonObject("orderDTO")), new OrderDTO(jsonObject.getJsonObject("orderDTO")), Instant.parse(jsonObject.getString("instant")));
	}
}
