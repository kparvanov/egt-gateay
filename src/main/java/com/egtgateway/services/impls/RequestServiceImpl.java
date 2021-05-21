package com.egtgateway.services.impls;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.egtgateway.dtos.CommandRequest;
import com.egtgateway.dtos.CurrentRateRequest;
import com.egtgateway.dtos.HistoryRateRequest;
import com.egtgateway.entities.Request;
import com.egtgateway.exceptions.RequestAlreadyExistsException;
import com.egtgateway.mappers.RequestMapper;
import com.egtgateway.repositories.RequestRepository;
import com.egtgateway.services.RequestService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;

    @Cacheable("requests")
    public boolean existsByRequestId(final String requestId) {
        return requestRepository.existsByRequestId(requestId);
    }

    @Override
    public boolean validateRequest(final String requestId) {
        if (existsByRequestId(requestId)) {
            throw new RequestAlreadyExistsException(requestId);
        }

        return true;
    }

    @Override
    public Request save(final CurrentRateRequest request) {
        return requestRepository.save(requestMapper.currentRateRequestToRequest(request));
    }

    @Override
    public Request save(final HistoryRateRequest request) {
        return requestRepository.save(requestMapper.historyRateRequestToRequest(request));
    }

    @Override
    public Request save(final CommandRequest request) {
        return requestRepository.save(requestMapper.commandRequestToRequest(request));
    }
}
