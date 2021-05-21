package com.egtgateway.jobs;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import com.egtgateway.dtos.FixedRatesResponse;
import com.egtgateway.services.RabbitMqSenderService;
import com.egtgateway.services.StatisticsCollectorService;

@RunWith(MockitoJUnitRunner.class)
public class RatesCollectorJobTest {

    @Mock
    private StatisticsCollectorService statisticsCollectorService;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private RabbitMqSenderService rabbitMqSenderService;

    @InjectMocks
    private RatesCollectorJob ratesCollectorJob;

    @Mock
    private FixedRatesResponse fixedRatesResponse;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(ratesCollectorJob, "apiName", "apiName");
        ReflectionTestUtils.setField(ratesCollectorJob, "path", "test");
        ReflectionTestUtils.setField(ratesCollectorJob, "apiKeyName", "test");
        ReflectionTestUtils.setField(ratesCollectorJob, "apiKeyValue", "test");
    }

    @Test
    public void shouldNotReceiveInformationForRates() {
        when(fixedRatesResponse.getSuccess()).thenReturn(false);
        when(restTemplate.getForObject(any(URI.class), any())).thenReturn(fixedRatesResponse);

        ratesCollectorJob.scheduleFixedRateTask();

        verify(statisticsCollectorService, never()).insertRates(fixedRatesResponse);
        verify(rabbitMqSenderService, never()).sendAsync(fixedRatesResponse);
    }

    @Test
    public void shouldNReceiveInformationForRates() {
        when(fixedRatesResponse.getSuccess()).thenReturn(true);
        when(restTemplate.getForObject(any(URI.class), any())).thenReturn(fixedRatesResponse);

        ratesCollectorJob.scheduleFixedRateTask();

        verify(statisticsCollectorService).insertRates(fixedRatesResponse);
        verify(rabbitMqSenderService).sendAsync(fixedRatesResponse);
    }
}
