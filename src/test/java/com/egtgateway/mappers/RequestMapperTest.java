package com.egtgateway.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import com.egtgateway.dtos.CommandGetDto;
import com.egtgateway.dtos.CommandHistoryDto;
import com.egtgateway.dtos.CommandRequest;
import com.egtgateway.dtos.CurrentRateRequest;
import com.egtgateway.dtos.HistoryRateRequest;
import com.egtgateway.entities.Request;

@RunWith(MockitoJUnitRunner.class)
class RequestMapperTest {

    private static final String BGN = "BGN";
    private static final String CLIENT = "client";
    private static final Integer PERIOD = 2;
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final String REQUEST_ID = UUID.randomUUID().toString();

    private final RequestMapper requestMapper = new RequestMapper();

    @Test
    public void currentRateRequestToRequest() {
        final CurrentRateRequest request = new CurrentRateRequest(REQUEST_ID,
                NOW.atZone(ZoneId.systemDefault()).toEpochSecond(), CLIENT, BGN);

        final Request result = requestMapper.currentRateRequestToRequest(request);

        assertTrue(Objects.nonNull(result));
        assertEquals(result.getClient(), CLIENT);
        assertEquals(result.getRequestId(), REQUEST_ID);
        assertEquals(result.getCurrency(), BGN);
        assertNull(result.getPeriod());
        assertEquals(result.getEndpoint(), RequestMapper.CURRENT_RATE_ENDPOINT);
        assertEquals(result.getServiceName(), RequestMapper.EXT_SERVICE_2);
    }

    @Test
    public void historyRateRequestToRequest() {
        final HistoryRateRequest request = new HistoryRateRequest(REQUEST_ID,
                NOW.atZone(ZoneId.systemDefault()).toEpochSecond(), CLIENT, BGN, PERIOD);

        final Request result = requestMapper.historyRateRequestToRequest(request);

        assertTrue(Objects.nonNull(result));
        assertEquals(result.getClient(), CLIENT);
        assertEquals(result.getRequestId(), REQUEST_ID);
        assertEquals(result.getCurrency(), BGN);
        assertEquals(result.getPeriod(), PERIOD);
        assertEquals(result.getEndpoint(), RequestMapper.HISTORY_RATE_ENDPOINT);
        assertEquals(result.getServiceName(), RequestMapper.EXT_SERVICE_2);
    }

    @Test
    public void commandRequestToRequestWithCurrentData() {
        final CommandGetDto commandGetDto = new CommandGetDto(CLIENT, BGN);
        final CommandRequest request = new CommandRequest(REQUEST_ID, commandGetDto, null);

        final Request result = requestMapper.commandRequestToRequest(request);

        assertTrue(Objects.nonNull(result));
        assertEquals(result.getClient(), CLIENT);
        assertEquals(result.getRequestId(), REQUEST_ID);
        assertEquals(result.getCurrency(), BGN);
        assertNull(result.getPeriod());
        assertEquals(result.getEndpoint(), RequestMapper.COMMAND_ENDPOINT);
        assertEquals(result.getServiceName(), RequestMapper.EXT_SERVICE_1);
    }

    @Test
    public void commandRequestToRequestWithHistoryDate() {
        final CommandHistoryDto commandHistoryDto = new CommandHistoryDto(CLIENT, BGN, PERIOD);
        final CommandRequest request = new CommandRequest(REQUEST_ID, null, commandHistoryDto);

        final Request result = requestMapper.commandRequestToRequest(request);

        assertTrue(Objects.nonNull(result));
        assertEquals(result.getClient(), CLIENT);
        assertEquals(result.getRequestId(), REQUEST_ID);
        assertEquals(result.getCurrency(), BGN);
        assertEquals(result.getPeriod(), PERIOD);
        assertEquals(result.getEndpoint(), RequestMapper.COMMAND_ENDPOINT);
        assertEquals(result.getServiceName(), RequestMapper.EXT_SERVICE_1);
    }
}