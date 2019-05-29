package contracts

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method 'GET'
        url '/order/123456'
    }
    response {
        status 200
        headers {
            contentType('application/json')
        }
        body('''{"id" : "123456", "customerId" : "1010"}''')
    }
}