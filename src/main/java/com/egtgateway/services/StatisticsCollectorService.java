package com.egtgateway.services;

import java.util.List;

import com.egtgateway.dtos.CommandRequest;
import com.egtgateway.dtos.CurrentRateResponse;
import com.egtgateway.dtos.FixedRatesResponse;
import com.egtgateway.dtos.HistoryRateResponse;
import com.egtgateway.dtos.CommandRateResponse;
import com.egtgateway.entities.Rate;

public interface StatisticsCollectorService {

    List<Rate> insertRates(FixedRatesResponse fixedRatesResponse);

    CurrentRateResponse getCurrentRate(String currency);

    HistoryRateResponse getHistoryRate(String currency, Integer period);

    CommandRateResponse getCommandRate(CommandRequest request);
}
