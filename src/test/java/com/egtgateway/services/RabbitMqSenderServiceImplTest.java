package com.egtgateway.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.egtgateway.dtos.FixedRatesResponse;
import com.egtgateway.entities.Request;
import com.egtgateway.services.impls.RabbitMqSenderServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class RabbitMqSenderServiceImplTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private RabbitMqSenderServiceImpl rabbitMqSenderService;

    @Mock
    private Request request;

    @Mock
    private FixedRatesResponse fixedRatesResponse;

    @Test
    public void sendMessageToRabbitWithRequestObject() {
        rabbitMqSenderService.sendAsync(request);

        verify(rabbitTemplate).convertAndSend(any(), eq(request));
    }

    @Test
    public void sendMessageToRabbitWithFixedRatesResponse() {
        rabbitMqSenderService.sendAsync(fixedRatesResponse);

        verify(rabbitTemplate).convertAndSend(any(), eq(fixedRatesResponse));
    }
}
