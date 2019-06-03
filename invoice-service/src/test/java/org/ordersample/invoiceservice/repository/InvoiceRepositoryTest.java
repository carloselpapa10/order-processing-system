package org.ordersample.invoiceservice.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ordersample.invoiceservice.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InvoiceJpaTestConfiguration.class)
public class InvoiceRepositoryTest {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Test
    public void shouldSaveAndLoadInvoice(){

        String invoiceId = transactionTemplate.execute((ts) -> {
            Invoice invoice = new Invoice("1", "111", "Invoice Comment");
            invoiceRepository.save(invoice);
            return invoice.getId();
        });

        transactionTemplate.execute((ts) -> {
            Invoice invoice = invoiceRepository.findById(invoiceId).get();

            assertNotNull(invoice);
            assertEquals("Invoice Comment", invoice.getInvoiceComment());
            assertEquals("111", invoice.getOrderId());

            return null;
        });
    }
}
