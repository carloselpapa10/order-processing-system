package org.ordersample.invoiceservice.impl;

import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.micrometer.core.instrument.MeterRegistry;
import org.ordersample.invoiceservice.repository.InvoiceRepository;
import org.ordersample.domaininfo.common.BusinessException;
import org.ordersample.domaininfo.invoice.api.events.*;
import org.ordersample.domaininfo.invoice.api.info.*;
import org.ordersample.invoiceservice.dao.InvoiceService;
import org.ordersample.invoiceservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static java.util.Arrays.asList;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class InvoiceServiceImpl implements InvoiceService{

	private static final Logger log = LoggerFactory.getLogger(InvoiceServiceImpl.class);

	private final InvoiceRepository invoiceRepository;

	@Autowired
	private DomainEventPublisher domainEventPublisher;

	private Optional<MeterRegistry> meterRegistry;

	public InvoiceServiceImpl(InvoiceRepository invoiceRepository, Optional<MeterRegistry> meterRegistry) {
		this.invoiceRepository = invoiceRepository;
		this.meterRegistry = meterRegistry;
	}

	@Override
	public Invoice createInvoice(Invoice invoice) throws BusinessException{
		log.info("InvoiceService - InvoiceServiceImpl - createInvoice");
		
		invoice = invoiceRepository.save(invoice);
		domainEventPublisher.publish(Invoice.class, invoice.getId(), asList(new InvoiceCreatedEvent(new InvoiceDTO(invoice.getId(), invoice.getOrderId(), invoice.getInvoiceComment()))));

		meterRegistry.ifPresent(mr -> mr.counter("created_invoice").increment());

		return invoice;
	}
				
	@Override
	public void rejectInvoice(Invoice invoice) throws BusinessException{
		log.info("InvoiceService - InvoiceServiceImpl - rejectInvoice");

		invoiceRepository.delete(invoice);
		domainEventPublisher.publish(Invoice.class, invoice.getId(), asList(new InvoiceCreationFailedEvent(new InvoiceDTO(invoice.getId()))));

		meterRegistry.ifPresent(mr -> mr.counter("rejected_invoice").increment());
	}
			
	@Override
	public Invoice findInvoice(String id) throws BusinessException{
		log.info("InvoiceService - InvoiceServiceImpl - findInvoice");
		return invoiceRepository.existsById(id) ? invoiceRepository.findById(id).get() : null;
	}
			
	@Override
	public List<Invoice> findAll() throws BusinessException{
		log.info("InvoiceService - InvoiceServiceImpl - findAll");
		return invoiceRepository.findAll();
	}
	
}
