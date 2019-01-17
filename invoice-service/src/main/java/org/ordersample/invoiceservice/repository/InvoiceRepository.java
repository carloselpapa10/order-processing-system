package org.ordersample.invoiceservice.repository;

import org.ordersample.invoiceservice.model.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends MongoRepository<Invoice,String>{

}
