package com.egtgateway.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.egtgateway.dtos.CommandGetDto;
import com.egtgateway.dtos.CommandHistoryDto;
import com.egtgateway.dtos.CommandRequest;
import com.egtgateway.dtos.CurrentRateRequest;
import com.egtgateway.dtos.HistoryRateRequest;
import com.egtgateway.entities.Request;
import com.egtgateway.exceptions.RequestAlreadyExistsException;
import com.egtgateway.mappers.RequestMapper;
import com.egtgateway.repositories.RequestRepository;
import com.egtgateway.services.impls.RequestServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class RequestServiceTest {

    private static final String BGN = "BGN";
    private static final String CLIENT = "client";
    private static final Integer PERIOD = 2;
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final String REQUEST_ID = UUID.randomUUID().toString();

    @Mock
    private RequestRepository requestRepository;
    @Mock
    private RequestMapper requestMapper;

    @InjectMocks
    private RequestServiceImpl requestService;

    @Test
    public void shouldReturnTrueWhenRequestIsNotExisting() {
        when(requestRepository.existsByRequestId(REQUEST_ID)).thenReturn(false);

        final boolean result = requestService.validateRequest(REQUEST_ID);

        assertTrue(result);
    }

    @Test(expected = RequestAlreadyExistsException.class)
    public void shouldThrowExceptionWhenRequestIsExisting() {
        when(requestRepository.existsByRequestId(REQUEST_ID)).thenReturn(true);

        requestService.validateRequest(REQUEST_ID);
    }

    @Test
    public void shouldSaveCurrentRateRequest() {
        final CurrentRateRequest currentRateRequest = new CurrentRateRequest(REQUEST_ID,
                NOW.atZone(ZoneId.systemDefault()).toEpochSecond(), CLIENT, BGN);

        final Request request = new Request(REQUEST_ID, NOW, CLIENT, BGN, null,
                RequestMapper.EXT_SERVICE_2, RequestMapper.CURRENT_RATE_ENDPOINT);

        when(requestRepository.save(any())).thenAnswer(answer -> answer.getArguments()[0]);
        when(requestMapper.currentRateRequestToRequest(currentRateRequest)).thenReturn(request);

        final Request result = requestService.save(currentRateRequest);

        assertEquals(result.getRequestId(), currentRateRequest.getRequestId());
        assertEquals(result.getClient(), currentRateRequest.getClient());
        assertEquals(result.getServiceName(), RequestMapper.EXT_SERVICE_2);
        assertEquals(result.getEndpoint(), RequestMapper.CURRENT_RATE_ENDPOINT);
        assertEquals(result.getTimestamp(), NOW);
        assertEquals(result.getCurrency(), currentRateRequest.getCurrency());
    }

    @Test
    public void shouldSaveHistoryRateRequest() {
        final HistoryRateRequest historyRateRequest = new HistoryRateRequest(REQUEST_ID,
                NOW.atZone(ZoneId.systemDefault()).toEpochSecond(), CLIENT, BGN, PERIOD);

        final Request request = new Request(REQUEST_ID, NOW, CLIENT, BGN, PERIOD,
                RequestMapper.EXT_SERVICE_2, RequestMapper.HISTORY_RATE_ENDPOINT);

        when(requestRepository.save(any())).thenAnswer(answer -> answer.getArguments()[0]);
        when(requestMapper.historyRateRequestToRequest(historyRateRequest)).thenReturn(request);

        final Request result = requestService.save(historyRateRequest);

        assertEquals(result.getRequestId(), historyRateRequest.getRequestId());
        assertEquals(result.getClient(), historyRateRequest.getClient());
        assertEquals(result.getServiceName(), RequestMapper.EXT_SERVICE_2);
        assertEquals(result.getEndpoint(), RequestMapper.HISTORY_RATE_ENDPOINT);
        assertEquals(result.getTimestamp(), NOW);
        assertEquals(result.getCurrency(), historyRateRequest.getCurrency());
        assertEquals(result.getPeriod(), historyRateRequest.getPeriod());
    }

    @Test
    public void shouldSaveCommandRequestWithHistory() {
        final CommandHistoryDto commandHistoryDto = new CommandHistoryDto(CLIENT, "BGN", PERIOD);

        final CommandRequest commandRequest = new CommandRequest(REQUEST_ID, null, commandHistoryDto);

        final Request request = new Request(REQUEST_ID, NOW, CLIENT, BGN, PERIOD,
                RequestMapper.EXT_SERVICE_1, RequestMapper.COMMAND_ENDPOINT);

        when(requestRepository.save(any())).thenAnswer(answer -> answer.getArguments()[0]);
        when(requestMapper.commandRequestToRequest(commandRequest)).thenReturn(request);

        final Request result = requestService.save(commandRequest);

        assertEquals(result.getRequestId(), commandRequest.getId());
        assertEquals(result.getClient(), commandRequest.getHistory().getConsumer());
        assertEquals(result.getServiceName(), RequestMapper.EXT_SERVICE_1);
        assertEquals(result.getEndpoint(), RequestMapper.COMMAND_ENDPOINT);
        assertEquals(result.getTimestamp(), NOW);
        assertEquals(result.getCurrency(), commandRequest.getHistory().getCurrency());
        assertEquals(result.getPeriod(), commandRequest.getHistory().getPeriod());
    }

    @Test
    public void shouldSaveCommandRequestWithCurrent() {
        final CommandGetDto commandGetDto = new CommandGetDto(CLIENT, "BGN");

        final CommandRequest commandRequest = new CommandRequest(REQUEST_ID, commandGetDto, null);

        final Request request = new Request(REQUEST_ID, NOW, CLIENT, BGN, null,
                RequestMapper.EXT_SERVICE_1, RequestMapper.COMMAND_ENDPOINT);

        when(requestRepository.save(any())).thenAnswer(answer -> answer.getArguments()[0]);
        when(requestMapper.commandRequestToRequest(commandRequest)).thenReturn(request);

        final Request result = requestService.save(commandRequest);

        assertEquals(result.getRequestId(), commandRequest.getId());
        assertEquals(result.getClient(), commandRequest.getGet().getConsumer());
        assertEquals(result.getServiceName(), RequestMapper.EXT_SERVICE_1);
        assertEquals(result.getEndpoint(), RequestMapper.COMMAND_ENDPOINT);
        assertEquals(result.getTimestamp(), NOW);
        assertEquals(result.getCurrency(), commandRequest.getGet().getCurrency());
        assertNull(result.getPeriod());
    }
}
