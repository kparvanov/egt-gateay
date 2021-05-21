package com.egtgateway.controllers.json;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.egtgateway.dtos.CurrentRateRequest;
import com.egtgateway.dtos.CurrentRateResponse;
import com.egtgateway.dtos.HistoryRateRequest;
import com.egtgateway.dtos.HistoryRateResponse;
import com.egtgateway.entities.Request;
import com.egtgateway.services.RabbitMqSenderService;
import com.egtgateway.services.RequestService;
import com.egtgateway.services.StatisticsCollectorService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(JsonApiController.PATH)
@RequiredArgsConstructor
public class JsonApiController {

    public static final String PATH = "/json_api";
    public static final String CURRENT_PATH = "/current";
    public static final String HISTORY_PATH = "/history";

    private final StatisticsCollectorService statisticsCollectorService;
    private final RequestService requestService;
    private final RabbitMqSenderService rabbitMqSenderService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = CURRENT_PATH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CurrentRateResponse currentRate(@Valid @RequestBody final CurrentRateRequest request) {
        final CurrentRateResponse currentRate = statisticsCollectorService.getCurrentRate(request.getCurrency());

        final Request savedRequest = requestService.save(request);

        rabbitMqSenderService.sendAsync(savedRequest);

        return currentRate;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = HISTORY_PATH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HistoryRateResponse historyRate(@Valid @RequestBody final HistoryRateRequest request) {
        final HistoryRateResponse historyRate = statisticsCollectorService.getHistoryRate(request.getCurrency(),
                request.getPeriod());

        final Request savedRequest = requestService.save(request);

        rabbitMqSenderService.sendAsync(savedRequest);

        return historyRate;
    }
}
