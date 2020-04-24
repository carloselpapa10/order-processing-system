package org.ordersample.orderservice.commandhandlers;

public class OrderServiceCommandHandlers {

//	private static final Logger log = LoggerFactory.getLogger(OrderServiceCommandHandlers.class);
//
//	@Autowired
//	private OrderService orderService;
//
//	public CommandHandlers commandHandlers() {
//		return SagaCommandHandlersBuilder
//				.fromChannel(Channels.ORDERSERVICE)
//				.onMessage(CompleteOrderCommand.class, this::handleCompleteOrderCommand)
//				.onMessage(RejectOrderCommand.class, this::handleRejectOrderCommand)
//				.onMessage(EditOrderCommand.class, this::handleEditOrderCommand)
//				.build();
//	}
//
//	private Message handleCompleteOrderCommand(CommandMessage<CompleteOrderCommand> cm) {
//		log.info("OrderService - OrderServiceCommandHandlers - handleCompleteOrderCommand");
//
//		CompleteOrderCommand command = cm.getCommand();
//
//		try {
//			Order order = orderService.findOrder(command.getOrderDTO().getId());
//
//			if (order != null) {
//				order.setInvoiceId(command.getOrderDTO().getInvoiceId());
//				orderService.completeOrder(order);
//				return withSuccess();
//			}
//		}catch (InvalidOrderIdException ex){
//			log.error(String.format("Invalid Order with ID: %s", command.getOrderDTO().getId()));
//		}
//
//		return withFailure();
//	}
//
//	private Message handleRejectOrderCommand(CommandMessage<RejectOrderCommand> cm) {
//		log.info("OrderService - OrderServiceCommandHandlers - handleRejectOrderCommand");
//
//		RejectOrderCommand command = cm.getCommand();
//
//		try{
//			Order order = orderService.findOrder(command.getOrderDTO().getId());
//
//			if(order != null) {
//				orderService.rejectOrder(order);
//				return withSuccess();
//			}
//		}catch (InvalidOrderIdException ex){
//			log.error(String.format("Invalid Order with ID: %s", command.getOrderDTO().getId()));
//		}
//
//		return withFailure();
//	}
//
//	private Message handleEditOrderCommand(CommandMessage<EditOrderCommand> cm) {
//		log.info("OrderService - OrderServiceCommandHandlers - handleEditOrderCommand");
//
//		EditOrderCommand command = cm.getCommand();
//
//		try{
//			Order order = orderService.findOrder(command.getOrderDTO().getId());
//
//			if(order != null) {
//				order.setDescription(command.getOrderDTO().getDescription());
//				order.setCustomerId(command.getOrderDTO().getCustomerId());
//				order.setInvoiceId(command.getOrderDTO().getInvoiceId());
//
//				orderService.editOrder(order);
//				return withSuccess();
//			}
//		}catch (InvalidOrderIdException ex){
//			log.error(String.format("Invalid Order with ID: %s", command.getOrderDTO().getId()));
//		}
//
//		return withFailure();
//	}

}
