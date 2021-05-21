package com.egtgateway.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import com.egtgateway.dtos.CurrentRateResponse;
import com.egtgateway.dtos.FixedRatesResponse;
import com.egtgateway.dtos.RateDateDto;
import com.egtgateway.entities.Rate;

@RunWith(MockitoJUnitRunner.class)
class RateMapperTest {

    private static final String BGN = "BGN";
    private static final BigDecimal BGN_RATE = new BigDecimal("1.954486");
    private static final BigDecimal EUR_RATE = new BigDecimal("1");
    private static final String EUR = "EUR";
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final Rate RATE = new Rate(UUID.randomUUID(), EUR, BGN, BGN_RATE, NOW);

    private final RateMapper rateMapper = new RateMapper();

    @Test
    void fixedRatesResponseToRatesList() {
        final FixedRatesResponse fixedRatesResponse = new FixedRatesResponse(true, System.currentTimeMillis(), EUR,
                NOW.toLocalDate(), Map.of("EUR", EUR_RATE, BGN, BGN_RATE));

        final List<Rate> result = rateMapper.fixedRatesResponseToRatesList(fixedRatesResponse);

        assertTrue(Objects.nonNull(result));
        assertEquals(result.size(), 2);
    }

    @Test
    void rateToCurrentRateResponse() {
        final CurrentRateResponse result = rateMapper.rateToCurrentRateResponse(RATE);

        assertTrue(Objects.nonNull(result));
        assertEquals(result.getRate(), BGN_RATE);
        assertEquals(result.getBase(), EUR);
        assertEquals(result.getCurrency(), BGN);
        assertEquals(result.getTimestamp(), NOW.atZone(ZoneId.systemDefault()).toEpochSecond());
    }

    @Test
    void rateToRateDateDto() {
        final RateDateDto result = rateMapper.rateToRateDateDto(RATE);

        assertTrue(Objects.nonNull(result));
        assertEquals(result.getRate(), BGN_RATE);
        assertEquals(result.getTimestamp(), NOW.atZone(ZoneId.systemDefault()).toEpochSecond());
    }
}