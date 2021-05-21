package com.egtgateway.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryRateResponse implements CommandRateResponse {

    private String currency;
    private String base;
    private List<RateDateDto> rateDates;
}
