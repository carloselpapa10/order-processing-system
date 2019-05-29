package org.ordersample.orderservice.exception;

public class InvalidOrderIdException extends BadRequestException{

    public InvalidOrderIdException(String message) {
        super(message);
    }
}
