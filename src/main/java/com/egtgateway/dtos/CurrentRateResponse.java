package com.egtgateway.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentRateResponse implements CommandRateResponse {

    private String currency;
    private BigDecimal rate;
    private String base;
    private Long timestamp;
}
