package contracts.messaging;

org.springframework.cloud.contract.spec.Contract.make {
    label 'requestInvoice'
    input {
        messageFrom 'invoiceservice'
        messageBody '''{"invoiceDTO": {"invoiceId":"1", "orderId": "111", "invoiceComment": "Invoice Comment"}}'''
        messageHeaders {
            header('command_type','org.ordersample.domaininfo.invoice.api.commands.RequestInvoiceCommand')
            header('command_saga_type','org.ordersample.orderservice.saga.createorder.CreateOrderSaga')
            header('command_saga_id', $(consumer(regex('[0-9a-f]{16}-[0-9a-f]{16}'))))
            header('command_reply_to','org.ordersample.orderservice.saga.createorder.CreateOrderSaga-reply')
        }
    }
    outputMessage {
        sentTo('org.ordersample.orderservice.saga.createorder.CreateOrderSaga-reply')
        body'''{"invoiceId":"1", "orderId": "111", "invoiceComment": "Invoice Comment"}'''
        headers {
            header('reply_type', 'org.ordersample.domaininfo.invoice.api.info.InvoiceDTO')
            header('reply_outcome-type', 'SUCCESS')
        }

    }
}