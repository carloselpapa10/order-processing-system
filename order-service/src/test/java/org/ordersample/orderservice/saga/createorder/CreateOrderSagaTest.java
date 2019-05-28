package org.ordersample.orderservice.saga.createorder;

import org.junit.Test;
import org.ordersample.domaininfo.customer.api.commands.ValidateCustomerByOrderService;
import org.ordersample.domaininfo.customer.api.info.CustomerDTO;
import org.ordersample.domaininfo.invoice.api.commands.RequestInvoiceCommand;
import org.ordersample.domaininfo.invoice.api.info.InvoiceDTO;
import org.ordersample.domaininfo.order.api.commands.CompleteOrderCommand;
import org.ordersample.domaininfo.order.api.commands.RejectOrderCommand;
import org.ordersample.domaininfo.order.api.info.OrderDTO;
import org.ordersample.orderservice.proxy.CustomerServiceProxy;
import org.ordersample.orderservice.proxy.InvoiceServiceProxy;
import org.ordersample.orderservice.proxy.OrderServiceProxy;
import static io.eventuate.tram.sagas.testing.SagaUnitTestSupport.given;
import static org.ordersample.domaininfo.common.Channels.*;
import static org.ordersample.orderservice.saga.OrderDetails.*;

public class CreateOrderSagaTest {

    private OrderServiceProxy orderServiceProxy = new OrderServiceProxy();
    private CustomerServiceProxy customerServiceProxy = new CustomerServiceProxy();
    private InvoiceServiceProxy invoiceServiceProxy = new InvoiceServiceProxy();

    private CreateOrderSaga makeCreateOrderSaga(){
        return new CreateOrderSaga(customerServiceProxy, orderServiceProxy, invoiceServiceProxy);
    }

    @Test
    public void shouldCreateOrder(){
        given().
                saga(makeCreateOrderSaga(), new CreateOrderSagaData(ORDER_ID, CUSTOMER_ID, INVOICE_ID)).
        expect().
                command(new ValidateCustomerByOrderService(new CustomerDTO(CUSTOMER_ID)))
                .to(CUSTOMERSERVICE).
        andGiven()
                .successReply().
        expect()
                .command(new RequestInvoiceCommand(new InvoiceDTO(INVOICE_ID, ORDER_ID, INVOICE_COMMENT)))
                .to(INVOICESERVICE).
        andGiven()
                .successReply().
        expect()
                .command(new CompleteOrderCommand(new OrderDTO(ORDER_ID)));
    }

    @Test
    public void shouldRejectDueToInvalidCustomerId(){
        given().
                saga(makeCreateOrderSaga(), new CreateOrderSagaData(ORDER_ID, CUSTOMER_ID, INVOICE_ID)).
        expect().command(new ValidateCustomerByOrderService(new CustomerDTO(CUSTOMER_ID)))
                .to(CUSTOMERSERVICE).
        andGiven()
                .failureReply().
        expect()
                .command(new RejectOrderCommand(new OrderDTO(ORDER_ID)))
                .to(ORDERSERVICE);
    }
}
