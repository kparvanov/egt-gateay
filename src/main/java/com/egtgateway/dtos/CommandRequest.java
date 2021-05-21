package com.egtgateway.dtos;

import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.egtgateway.validators.CommandRequestValidation;
import com.egtgateway.validators.RequestValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@CommandRequestValidation
@XmlRootElement(name = "command")
public class CommandRequest {

    @XmlAttribute
    @NotBlank
    @RequestValidation
    private String id;

    private CommandGetDto get;

    private CommandHistoryDto history;
}
