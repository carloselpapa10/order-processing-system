package org.ordersample.orderservice.saga.createorder;

import org.ordersample.domaininfo.customer.api.info.CustomerDTO;
import org.ordersample.domaininfo.invoice.api.info.InvoiceDTO;
import org.ordersample.domaininfo.order.api.info.OrderDTO;
import org.ordersample.orderservice.dao.OrderService;
import org.ordersample.orderservice.proxy.*;
import org.ordersample.domaininfo.customer.api.commands.*;
import org.ordersample.domaininfo.order.api.commands.*;
import org.ordersample.domaininfo.invoice.api.commands.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;

@Component
public class CreateOrderSaga implements SimpleSaga<CreateOrderSagaData>{
	
	private static final Logger log = LoggerFactory.getLogger(CreateOrderSaga.class);

	@Autowired
	private OrderService orderService;
	
	private SagaDefinition<CreateOrderSagaData> sagaDefinition;
	
	public CreateOrderSaga(CustomerServiceProxy customerService, OrderServiceProxy orderService, InvoiceServiceProxy invoiceService){
		
		this.sagaDefinition =
				step()					
					.withCompensation(orderService.rejectOrderCommand, this::makeRejectOrderCommand)			
				.step()
					.invokeParticipant(customerService.validateCustomerByOrderService, this::makeValidateCustomerByOrderService)
				.step()
					.invokeParticipant(invoiceService.requestInvoiceCommand, this::makeRequestInvoiceCommand)
					.onReply(InvoiceDTO.class, this::handleRequestInvoiceCommand)
					.withCompensation(invoiceService.compensateInvoiceCommand, this::makeCompensateInvoiceCommand)			
				.step()
					.invokeParticipant(orderService.completeOrderCommand, this::makeCompleteOrderCommand)
				.build();
	}

	@Override
	public SagaDefinition<CreateOrderSagaData> getSagaDefinition() {
		return sagaDefinition;
	}

	private RejectOrderCommand makeRejectOrderCommand(CreateOrderSagaData data) {
		log.info("OrderService - CreateOrderSaga - makeRejectOrderCommand");
		return new RejectOrderCommand(new OrderDTO(data.getId()));
	}

	private ValidateCustomerByOrderService makeValidateCustomerByOrderService(CreateOrderSagaData data) {
		log.info("OrderService - CreateOrderSaga - makeValidateCustomerByOrderService");
		return new ValidateCustomerByOrderService(new CustomerDTO(data.getCustomerId()));
	}

	private RequestInvoiceCommand makeRequestInvoiceCommand(CreateOrderSagaData data) {
		log.info("OrderService - CreateOrderSaga - makeRequestInvoiceCommand");
		return new RequestInvoiceCommand(new InvoiceDTO("", data.getId(), "Invoice Comment"));
	}

	private void handleRequestInvoiceCommand(CreateOrderSagaData data, InvoiceDTO invoiceDTO) {
		log.info("OrderService - CreateOrderSaga - handleRequestInvoiceCommand");
		orderService.updateInvoiceOrder(data.getId(), invoiceDTO.getInvoiceId());
	}

	private CompensateInvoiceCommand makeCompensateInvoiceCommand(CreateOrderSagaData data) {
		log.info("OrderService - CreateOrderSaga - makeCompensateInvoiceCommand");
		return new CompensateInvoiceCommand(new InvoiceDTO(data.getInvoiceId(), data.getId(), ""));
	}

	private CompleteOrderCommand makeCompleteOrderCommand(CreateOrderSagaData data) {
		log.info("OrderService - CreateOrderSaga - makeCompleteOrderCommand");
		return new CompleteOrderCommand(new OrderDTO(data.getId()));
	}

}
