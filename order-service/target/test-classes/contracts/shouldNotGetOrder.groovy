package contracts

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method 'GET'
        url '/order/321'
    }
    response {
        status BAD_REQUEST()
    }
}