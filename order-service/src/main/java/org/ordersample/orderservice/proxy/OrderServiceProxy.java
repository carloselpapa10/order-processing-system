package org.ordersample.orderservice.proxy;

import org.springframework.stereotype.Component;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;
import org.ordersample.domaininfo.common.Channels;
import io.eventuate.tram.commands.common.Success;
import org.ordersample.domaininfo.order.api.commands.*;
import org.ordersample.domaininfo.order.api.info.*;

@Component
public class OrderServiceProxy {

	public final CommandEndpoint<RejectOrderCommand> rejectOrderCommand = CommandEndpointBuilder
								.forCommand(RejectOrderCommand.class)
								.withChannel(Channels.ORDERSERVICE)
								.withReply(Success.class)
								.build();			

	public final CommandEndpoint<EditOrderCommand> editOrderCommand = CommandEndpointBuilder
								.forCommand(EditOrderCommand.class)
								.withChannel(Channels.ORDERSERVICE)
								.withReply(Success.class)
								.build();			

	public final CommandEndpoint<CompleteOrderCommand> completeOrderCommand = CommandEndpointBuilder
								.forCommand(CompleteOrderCommand.class)
								.withChannel(Channels.ORDERSERVICE)
								.withReply(Success.class)
								.build();			

}					
