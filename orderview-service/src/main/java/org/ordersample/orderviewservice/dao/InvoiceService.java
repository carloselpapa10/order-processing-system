package org.ordersample.orderviewservice.dao;

import java.util.List;
import org.ordersample.domaininfo.common.BusinessException;
import org.ordersample.orderviewservice.model.*;

public interface InvoiceService {

	public Invoice createInvoice(Invoice invoice) throws BusinessException;				
	public void rejectInvoice(String id) throws BusinessException;			
	public Invoice findInvoice(String id) throws BusinessException;			
	public List<Invoice> findAll() throws BusinessException;

}	
