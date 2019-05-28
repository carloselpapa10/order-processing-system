package org.ordersample.domaininfo.order.api.info;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Setter
@Getter
@NoArgsConstructor
public class OrderDTO {

	private String id; 
    private String description;									
    private String customerId;
    private String invoiceId;

	public OrderDTO(String id) {
		this.id = id;
	}

	public OrderDTO(String id, String invoiceId) {
		this.id = id;
		this.invoiceId = invoiceId;
	}

	public OrderDTO(String id, String description, String customerId, String invoiceId) {
		this.id = id;
		this.description = description;
		this.customerId = customerId;
		this.invoiceId = invoiceId;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
}
