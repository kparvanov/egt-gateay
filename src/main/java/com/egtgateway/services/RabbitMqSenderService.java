package com.egtgateway.services;

import com.egtgateway.dtos.FixedRatesResponse;
import com.egtgateway.entities.Request;

public interface RabbitMqSenderService {

    void sendAsync(Request request);

    void sendAsync(FixedRatesResponse response);
}
