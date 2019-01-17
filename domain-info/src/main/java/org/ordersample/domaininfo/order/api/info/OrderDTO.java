package org.ordersample.domaininfo.order.api.info;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderDTO {

	private String id; 
    private String description;									
    private String customerId;
    private String invoiceId;

	public OrderDTO(String id, String description, String customerId, String invoiceId) {
		this.id = id;
		this.description = description;
		this.customerId = customerId;
		this.invoiceId = invoiceId;
	}
}
