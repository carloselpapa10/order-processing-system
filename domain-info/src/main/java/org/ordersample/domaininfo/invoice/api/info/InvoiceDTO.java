package org.ordersample.domaininfo.invoice.api.info;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Setter
@Getter
@NoArgsConstructor
public class InvoiceDTO {

	private String invoiceId;
    private String orderId;
    private String invoiceComment;

	public InvoiceDTO(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public InvoiceDTO(String invoiceId, String orderId, String invoiceComment) {
		this.invoiceId = invoiceId;
		this.orderId = orderId;
		this.invoiceComment = invoiceComment;
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
