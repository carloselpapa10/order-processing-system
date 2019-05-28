package org.ordersample.orderservice.saga.createorder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Setter
@Getter
@NoArgsConstructor
public class CreateOrderSagaData {

	private String id; 
    private String customerId;
    private String invoiceId;

	public CreateOrderSagaData(String id, String customerId, String invoiceId) {
		this.id = id;
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
