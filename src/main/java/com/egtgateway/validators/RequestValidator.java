package com.egtgateway.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import com.egtgateway.exceptions.RequestAlreadyExistsException;
import com.egtgateway.services.RequestService;

public class RequestValidator implements ConstraintValidator<RequestValidation, String> {

    @Autowired
    private RequestService requestService;

    @Override
    public void initialize(final RequestValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        try {
            return requestService.validateRequest(value);
        } catch (RequestAlreadyExistsException exception) {
            return false;
        }
    }
}
