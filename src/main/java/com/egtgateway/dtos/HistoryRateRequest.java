package com.egtgateway.dtos;

import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HistoryRateRequest extends CurrentRateRequest {

    @Min(1)
    private Integer period;

    public HistoryRateRequest(final String requestId, final Long timestamp, final String client,
                              final String currency, final Integer period) {
        super(requestId, timestamp, client, currency);

        this.period = period;
    }
}
