package com.egtgateway.controllers.xml;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.egtgateway.dtos.CommandRequest;
import com.egtgateway.dtos.CommandRateResponse;
import com.egtgateway.entities.Request;
import com.egtgateway.services.RabbitMqSenderService;
import com.egtgateway.services.RequestService;
import com.egtgateway.services.StatisticsCollectorService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(XmlApiController.PATH)
@RequiredArgsConstructor
public class XmlApiController {

    public static final String PATH = "/xml_api";
    public static final String COMMAND_PATH = "/command";

    private final StatisticsCollectorService statisticsCollectorService;
    private final RequestService requestService;
    private final RabbitMqSenderService rabbitMqSenderService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = COMMAND_PATH,
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE)
    public CommandRateResponse commandRate(@Valid @RequestBody final CommandRequest request) {
        final CommandRateResponse resultRate = statisticsCollectorService.getCommandRate(request);

        final Request savedRequest = requestService.save(request);

        rabbitMqSenderService.sendAsync(savedRequest);

        return resultRate;
    }
}
