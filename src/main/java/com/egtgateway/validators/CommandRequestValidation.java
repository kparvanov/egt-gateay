package com.egtgateway.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = CommandRequestValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandRequestValidation {

    String message() default "You should specify only one option of 'get' tag or 'history' tag";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
