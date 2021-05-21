package com.egtgateway.mappers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import com.egtgateway.dtos.CurrentRateResponse;
import com.egtgateway.dtos.FixedRatesResponse;
import com.egtgateway.dtos.RateDateDto;
import com.egtgateway.entities.Rate;

@Component
public class RateMapper {

    public List<Rate> fixedRatesResponseToRatesList(final FixedRatesResponse fixedRatesResponse) {
        return fixedRatesResponse.getRates().entrySet().stream()
                .map(rate -> new Rate(fixedRatesResponse.getBase(), rate.getKey(), rate.getValue()))
                .collect(Collectors.toList());
    }

    public CurrentRateResponse rateToCurrentRateResponse(final Rate rate) {
        final long timestamp = getTimestamp(rate.getTimestamp());

        return new CurrentRateResponse(rate.getCurrency(), rate.getRate(), rate.getBase(), timestamp);
    }

    public RateDateDto rateToRateDateDto(final Rate rate) {
        return new RateDateDto(getTimestamp(rate.getTimestamp()), rate.getRate());
    }

    private static long getTimestamp(final LocalDateTime timestamp) {
        return timestamp.atZone(ZoneId.systemDefault()).toEpochSecond();
    }
}
