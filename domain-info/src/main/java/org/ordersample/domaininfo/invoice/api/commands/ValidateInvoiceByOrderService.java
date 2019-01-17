package org.ordersample.domaininfo.invoice.api.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ordersample.domaininfo.invoice.api.info.InvoiceDTO;

import io.eventuate.tram.commands.common.Command;

@Setter
@Getter
@NoArgsConstructor
public class ValidateInvoiceByOrderService implements Command{

	private InvoiceDTO invoiceDTO;

	public ValidateInvoiceByOrderService(InvoiceDTO invoiceDTO) {
		this.invoiceDTO = invoiceDTO;
	}
}
