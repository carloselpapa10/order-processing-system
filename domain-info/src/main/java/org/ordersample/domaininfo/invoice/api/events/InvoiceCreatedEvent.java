package org.ordersample.domaininfo.invoice.api.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ordersample.domaininfo.invoice.api.info.InvoiceDTO;

@Setter
@Getter
@NoArgsConstructor
public class InvoiceCreatedEvent implements InvoiceDomainEvent{

	private InvoiceDTO invoiceDTO;

	public InvoiceCreatedEvent(InvoiceDTO invoiceDTO) {
		this.invoiceDTO = invoiceDTO;
	}
}
