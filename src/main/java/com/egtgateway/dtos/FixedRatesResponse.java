package com.egtgateway.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FixedRatesResponse implements Serializable {

    private Boolean success;
    private Long timestamp;
    private String base;
    private LocalDate date;
    private Map<String, BigDecimal> rates;
}
