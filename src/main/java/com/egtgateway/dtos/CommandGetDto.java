package com.egtgateway.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "get")
public class CommandGetDto {

    @XmlAttribute
    @NotBlank
    private String consumer;

    @XmlElement
    @Size(min = 3, max = 3)
    private String currency;
}
