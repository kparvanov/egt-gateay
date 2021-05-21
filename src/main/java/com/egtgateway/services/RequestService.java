package com.egtgateway.services;

import com.egtgateway.dtos.CommandRequest;
import com.egtgateway.dtos.CurrentRateRequest;
import com.egtgateway.dtos.HistoryRateRequest;
import com.egtgateway.entities.Request;

public interface RequestService {

    boolean existsByRequestId(String requestId);

    boolean validateRequest(String requestId);

    Request save(CurrentRateRequest request);

    Request save(HistoryRateRequest request);

    Request save(CommandRequest request);
}
