package com.egtgateway.validators;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.egtgateway.validators.CommandRequestValidation;
import com.egtgateway.dtos.CommandGetDto;
import com.egtgateway.dtos.CommandHistoryDto;
import com.egtgateway.dtos.CommandRequest;

public class CommandRequestValidator implements ConstraintValidator<CommandRequestValidation, CommandRequest> {

    @Override
    public void initialize(final CommandRequestValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(final CommandRequest value, final ConstraintValidatorContext context) {
        final CommandGetDto current = value.getGet();
        final CommandHistoryDto history = value.getHistory();

        return (!isNull(current) || !isNull(history)) && (!nonNull(current) || !nonNull(history));
    }
}
