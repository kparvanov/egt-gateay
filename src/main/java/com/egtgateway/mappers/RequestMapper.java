package com.egtgateway.mappers;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.TimeZone;

import org.springframework.stereotype.Component;
import com.egtgateway.controllers.json.JsonApiController;
import com.egtgateway.controllers.xml.XmlApiController;
import com.egtgateway.dtos.CommandGetDto;
import com.egtgateway.dtos.CommandHistoryDto;
import com.egtgateway.dtos.CommandRequest;
import com.egtgateway.dtos.CurrentRateRequest;
import com.egtgateway.dtos.HistoryRateRequest;
import com.egtgateway.entities.Request;

@Component
public class RequestMapper {

    public static final String EXT_SERVICE_1 = "EXT_SERVICE_1";
    public static final String EXT_SERVICE_2 = "EXT_SERVICE_2";
    public static final String CURRENT_RATE_ENDPOINT = JsonApiController.PATH + JsonApiController.CURRENT_PATH;
    public static final String HISTORY_RATE_ENDPOINT = JsonApiController.PATH + JsonApiController.HISTORY_PATH;
    public static final String COMMAND_ENDPOINT = XmlApiController.PATH + XmlApiController.COMMAND_PATH;

    public Request currentRateRequestToRequest(final CurrentRateRequest request) {
        final LocalDateTime timestamp = getLocalDateTime(request.getTimestamp());

        return new Request(request.getRequestId(), timestamp, request.getClient(), request.getCurrency(),
                           null, EXT_SERVICE_2, CURRENT_RATE_ENDPOINT);
    }

    public Request historyRateRequestToRequest(final HistoryRateRequest request) {
        final LocalDateTime timestamp = getLocalDateTime(request.getTimestamp());

        return new Request(request.getRequestId(), timestamp, request.getClient(), request.getCurrency(),
                           request.getPeriod(), EXT_SERVICE_2, HISTORY_RATE_ENDPOINT);
    }

    public Request commandRequestToRequest(final CommandRequest request) {
        if (Objects.nonNull(request.getGet())) {
            return buildFromCurrentRateRequest(request);
        }

        return buildFromHistoryRateRequest(request);
    }

    private static LocalDateTime getLocalDateTime(final Long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),
                                       TimeZone.getDefault().toZoneId());
    }

    private static Request buildFromCurrentRateRequest(final CommandRequest request) {
        final CommandGetDto currentRate = request.getGet();
        final String client = currentRate.getConsumer().trim();
        final String currency = currentRate.getCurrency().trim();

        return new Request(request.getId().trim(), getLocalDateTime(System.currentTimeMillis()), client, currency,
                           null, EXT_SERVICE_1, COMMAND_ENDPOINT);
    }

    private static Request buildFromHistoryRateRequest(final CommandRequest request) {
        final CommandHistoryDto history = request.getHistory();
        final String client = history.getConsumer().trim();
        final String currency = history.getCurrency().trim();
        final Integer period = history.getPeriod();

        return new Request(request.getId().trim(), getLocalDateTime(System.currentTimeMillis()), client, currency,
                           period, EXT_SERVICE_1, COMMAND_ENDPOINT);
    }
}
