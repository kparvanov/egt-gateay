package com.egtgateway.exceptions;

public class RequestAlreadyExistsException extends RuntimeException {

    public RequestAlreadyExistsException(final String requestId) {
        super(String.format("Request with id %s already exists", requestId));
    }
}
