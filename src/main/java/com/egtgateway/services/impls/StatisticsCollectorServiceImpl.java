package com.egtgateway.services.impls;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.egtgateway.dtos.CommandGetDto;
import com.egtgateway.dtos.CommandHistoryDto;
import com.egtgateway.dtos.CommandRequest;
import com.egtgateway.dtos.CurrentRateResponse;
import com.egtgateway.dtos.FixedRatesResponse;
import com.egtgateway.dtos.HistoryRateResponse;
import com.egtgateway.dtos.CommandRateResponse;
import com.egtgateway.dtos.RateDateDto;
import com.egtgateway.entities.Rate;
import com.egtgateway.exceptions.RateNotFoundException;
import com.egtgateway.mappers.RateMapper;
import com.egtgateway.repositories.RateRepository;
import com.egtgateway.services.StatisticsCollectorService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticsCollectorServiceImpl implements StatisticsCollectorService {

    private final RateRepository rateRepository;
    private final RateMapper rateMapper;

    @CacheEvict(cacheNames = { "currentRates", "historyRates" }, allEntries = true)
    @Override
    public List<Rate> insertRates(final FixedRatesResponse fixedRatesResponse) {
        final List<Rate> rates = rateMapper.fixedRatesResponseToRatesList(fixedRatesResponse);

        return StreamSupport.stream(rateRepository.saveAll(rates).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Cacheable("currentRates")
    @Override
    public CurrentRateResponse getCurrentRate(final String currency) {
        return rateRepository.findFirstByCurrencyOrderByTimestampDesc(currency)
                .map(rateMapper::rateToCurrentRateResponse)
                .orElseThrow(() -> new RateNotFoundException(currency));
    }

    @Cacheable("historyRates")
    @Override
    public HistoryRateResponse getHistoryRate(final String currency, final Integer period) {
        final LocalDateTime now = LocalDateTime.now();

        final List<Rate> rateHistory = rateRepository.findByCurrencyAndTimestampBetween(currency,
                now.minusHours(period), now);

        if (CollectionUtils.isEmpty(rateHistory)) {
            throw new RateNotFoundException(currency);
        }

        final List<RateDateDto> rateDates = rateHistory.stream()
                .map(rateMapper::rateToRateDateDto)
                .collect(Collectors.toList());

        final String base = rateHistory.get(0).getBase();

        return new HistoryRateResponse(currency, base, rateDates);
    }

    @Cacheable({ "currentRates", "historyRates" })
    @Override
    public CommandRateResponse getCommandRate(final CommandRequest request) {
        final CommandGetDto currentRate = request.getGet();

        if (Objects.nonNull(currentRate)) {
            return getCurrentRate(currentRate.getCurrency().trim());
        }

        final CommandHistoryDto historyRate = request.getHistory();

        return getHistoryRate(historyRate.getCurrency().trim(), historyRate.getPeriod());
    }
}
