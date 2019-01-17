package org.ordersample.invoiceservice.dao;

import java.util.List;
import org.ordersample.domaininfo.common.BusinessException;
import org.ordersample.invoiceservice.model.*;

public interface InvoiceService {

	public Invoice createInvoice(Invoice invoice) throws BusinessException;				
	public void rejectInvoice(Invoice invoice) throws BusinessException;			
	public Invoice findInvoice(String id) throws BusinessException;			
	public List<Invoice> findAll() throws BusinessException;
}		   
