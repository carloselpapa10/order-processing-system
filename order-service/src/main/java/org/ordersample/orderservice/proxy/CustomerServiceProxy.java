package org.ordersample.orderservice.proxy;

import org.springframework.stereotype.Component;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;
import org.ordersample.domaininfo.common.Channels;
import io.eventuate.tram.commands.common.Success;
import org.ordersample.domaininfo.customer.api.commands.*;

@Component
public class CustomerServiceProxy {

	public final CommandEndpoint<ValidateCustomerByOrderService> validateCustomerByOrderService = CommandEndpointBuilder
								.forCommand(ValidateCustomerByOrderService.class)
								.withChannel(Channels.CUSTOMERSERVICE)
								.withReply(Success.class)
								.build();			

}					
