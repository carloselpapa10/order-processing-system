package org.ordersample.orderviewservice.impl;

import org.ordersample.domaininfo.common.BusinessException;
import org.ordersample.orderviewservice.dao.InvoiceService;
import org.ordersample.orderviewservice.model.*;
import org.ordersample.orderviewservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Component
@Transactional
public class InvoiceServiceImpl implements InvoiceService{

	private static final Logger log = LoggerFactory.getLogger(InvoiceServiceImpl.class);

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Override
	public Invoice createInvoice(Invoice invoice) throws BusinessException{
		log.info("OrderViewService - InvoiceServiceImpl - createInvoice");
		return invoiceRepository.save(invoice);
	}
				
	@Override
	public void rejectInvoice(String id) throws BusinessException{
		log.info("OrderViewService - InvoiceServiceImpl - rejectInvoice");
		invoiceRepository.delete(findInvoice(id));
	}
			
	@Override
	public Invoice findInvoice(String id) throws BusinessException{
		log.info("OrderViewService - InvoiceServiceImpl - findInvoice");
		return invoiceRepository.existsById(id) ? invoiceRepository.findById(id).get() : null;
	}
			
	@Override
	public List<Invoice> findAll() throws BusinessException{
		log.info("OrderViewService - InvoiceServiceImpl - findAll");
		return invoiceRepository.findAll();
	}

}
