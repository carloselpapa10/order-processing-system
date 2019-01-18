package org.ordersample.invoiceservice.controller;

import org.ordersample.invoiceservice.dao.InvoiceService;
import org.ordersample.invoiceservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("invoice")
public class InvoiceServiceController {

	private static final Logger log = LoggerFactory.getLogger(InvoiceServiceController.class);

	@Autowired
	private InvoiceService invoiceService;

	@GetMapping("{invoiceId}")
	public Invoice findInvoice(@PathVariable("invoiceId") String id){
		log.info("InvoiceService - InvoiceServiceController - findInvoice");
		return invoiceService.findInvoice(id);
	} 			

	@GetMapping
	public List<Invoice> findAllInvoices(){
		log.info("InvoiceService - InvoiceServiceController - findAllInvoices");
		return invoiceService.findAll();
	}

}


