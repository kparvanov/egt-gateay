package com.egtgateway.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = RequestValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestValidation {

    String message() default "Request with this id already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
