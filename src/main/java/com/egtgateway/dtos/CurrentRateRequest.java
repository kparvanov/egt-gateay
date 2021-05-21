package com.egtgateway.dtos;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.egtgateway.validators.RequestValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentRateRequest implements Serializable {

    @NotBlank
    @RequestValidation
    private String requestId;

    private Long timestamp;

    @NotBlank
    private String client;

    @Size(min = 3, max = 3)
    private String currency;
}
