package org.ordersample.orderservice.saga.updateorder;

import org.ordersample.domaininfo.customer.api.info.CustomerDTO;
import org.ordersample.domaininfo.invoice.api.info.InvoiceDTO;
import org.ordersample.domaininfo.order.api.info.OrderDTO;
import org.ordersample.orderservice.proxy.*;
import org.ordersample.domaininfo.customer.api.commands.*;
import org.ordersample.domaininfo.order.api.commands.*;
import org.ordersample.domaininfo.invoice.api.commands.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;

@Component
public class UpdateOrderSaga implements SimpleSaga<UpdateOrderSagaData>{
	
	private static final Logger log = LoggerFactory.getLogger(UpdateOrderSaga.class);

	private SagaDefinition<UpdateOrderSagaData> sagaDefinition;
	
	public UpdateOrderSaga(CustomerServiceProxy customerService, OrderServiceProxy orderService, InvoiceServiceProxy invoiceService){
		
		this.sagaDefinition =
				step()					
					.invokeParticipant(customerService.validateCustomerByOrderService, this::makeValidateCustomerByOrderService)
				.step()
					.invokeParticipant(invoiceService.validateInvoiceByOrderService, this::makeValidateInvoiceByOrderService)
				.step()
					.invokeParticipant(orderService.editOrderCommand, this::makeEditOrderCommand)
				.build();
	}

	@Override
	public SagaDefinition<UpdateOrderSagaData> getSagaDefinition() {
		return sagaDefinition;
	}

	private ValidateCustomerByOrderService makeValidateCustomerByOrderService(UpdateOrderSagaData data) {
		log.info("OrderService - UpdateOrderSaga - makeValidateCustomerByOrderService");
		return new ValidateCustomerByOrderService(new CustomerDTO(data.getCustomerId()));
	}

	private ValidateInvoiceByOrderService makeValidateInvoiceByOrderService(UpdateOrderSagaData data) {
		log.info("OrderService - UpdateOrderSaga - makeValidateInvoiceByOrderService");
		return new ValidateInvoiceByOrderService(new InvoiceDTO(data.getInvoiceId(), data.getId(), ""));
	}

	private EditOrderCommand makeEditOrderCommand(UpdateOrderSagaData data) {
		log.info("OrderService - UpdateOrderSaga - makeEditOrderCommand");
		return new EditOrderCommand(new OrderDTO(data.getId(), data.getDescription(), data.getCustomerId(), data.getInvoiceId()));
	}

}
