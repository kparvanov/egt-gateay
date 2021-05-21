package com.egtgateway.services.impls;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.egtgateway.dtos.FixedRatesResponse;
import com.egtgateway.entities.Request;
import com.egtgateway.services.RabbitMqSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMqSenderServiceImpl implements RabbitMqSenderService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queue}")
    private String queueName;

    @Async
    @Override
    public void sendAsync(final Request request) {
        rabbitTemplate.convertAndSend(queueName, request);

        log.info("Message sent!");
    }

    @Async
    @Override
    public void sendAsync(final FixedRatesResponse response) {
        rabbitTemplate.convertAndSend(queueName, response);

        log.info("Message sent!");
    }
}
