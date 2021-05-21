package com.egtgateway.jobs;

import java.net.URI;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.egtgateway.dtos.FixedRatesResponse;
import com.egtgateway.services.RabbitMqSenderService;
import com.egtgateway.services.StatisticsCollectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class RatesCollectorJob {

    private final StatisticsCollectorService statisticsCollectorService;
    private final RestTemplate restTemplate;
    private final RabbitMqSenderService rabbitMqSenderService;

    @Value("${job.rates-collector.api-name}")
    private String apiName;

    @Value("${job.rates-collector.path}")
    private String path;

    @Value("${job.rates-collector.api-key.name}")
    private String apiKeyName;

    @Value("${job.rates-collector.api-key.value}")
    private String apiKeyValue;

    @Scheduled(fixedRateString = "${job.rates-collector.fixed-rate}")
    public void scheduleFixedRateTask() {
        final URI uri = UriComponentsBuilder.fromUriString(apiName)
                .path(path)
                .queryParam(apiKeyName, apiKeyValue)
                .build()
                .toUri();

        final FixedRatesResponse fixedRatesResponse = restTemplate.getForObject(uri, FixedRatesResponse.class);

        if (Objects.isNull(fixedRatesResponse) || !fixedRatesResponse.getSuccess()) {
            log.error("There are errors in rates collector job and rates are not updated");

            return;
        }

        statisticsCollectorService.insertRates(fixedRatesResponse);

        rabbitMqSenderService.sendAsync(fixedRatesResponse);

        log.info("Rates are not updated successfully");
    }
}
