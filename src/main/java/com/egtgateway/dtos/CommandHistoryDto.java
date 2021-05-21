package com.egtgateway.dtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "history")
public class CommandHistoryDto {

    @XmlAttribute
    @NotBlank
    private String consumer;

    @XmlAttribute
    @Size(min = 3, max = 3)
    private String currency;

    @XmlAttribute
    @Min(1)
    private Integer period;
}
