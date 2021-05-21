package com.egtgateway.exceptions;

public class RateNotFoundException extends RuntimeException {

    public RateNotFoundException(final String currency) {
        super(String.format("There is no rate information for currency: '%s'", currency));
    }
}
