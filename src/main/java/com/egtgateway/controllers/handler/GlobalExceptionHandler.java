package com.egtgateway.controllers.handler;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.egtgateway.dtos.ApiResponseDto;
import com.egtgateway.exceptions.RateNotFoundException;
import com.egtgateway.exceptions.RequestAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ RequestAlreadyExistsException.class, RateNotFoundException.class })
    public ApiResponseDto handleBadRequestExceptions(RuntimeException exception) {
        log.warn(exception.getMessage(), exception);

        return new ApiResponseDto(false, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponseDto handleArgumentNotValid(MethodArgumentNotValidException exception) {
        final Map<String, String> fieldErrors = exception.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        final Map<String, String> globalErrors = exception.getBindingResult().getGlobalErrors().stream()
                .collect(Collectors.toMap(ObjectError::getObjectName,
                                          DefaultMessageSourceResolvable::getDefaultMessage));

        globalErrors.putAll(fieldErrors);

        log.warn(exception.getMessage(), exception);

        return new ApiResponseDto(false, globalErrors.toString());
    }
}
