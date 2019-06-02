package contracts.message

org.springframework.cloud.contract.spec.Contract.make {
    label 'orderCreatedEvent'
    input{
        triggeredBy('orderCreated()')
    }
    outputMessage {
        sentTo('org.ordersample.orderservice.model.Order')
        body('''{"orderDTO":{"id":"111","description":"Order Description","customerId":"1010","invoiceId":"123"}}''')
        headers {
            header('event-aggregate-type', 'org.ordersample.orderservice.model.Order')
            header('event-type', 'org.ordersample.domaininfo.order.api.events.OrderCreatedEvent')
            header('event-aggregate-id', '111')
        }
    }
}