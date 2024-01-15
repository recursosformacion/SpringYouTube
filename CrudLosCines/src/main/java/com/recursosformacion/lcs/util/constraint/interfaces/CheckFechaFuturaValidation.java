package com.recursosformacion.lcs.util.constraint.interfaces;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.recursosformacion.lcs.util.constraint.validator.CheckFechaFuturaValidator;

import java.lang.annotation.RetentionPolicy;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

import jakarta.validation.Payload;
import jakarta.validation.Constraint;

@Target( { FIELD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CheckFechaFuturaValidator.class)
public @interface CheckFechaFuturaValidation {
    public String message() default "La fecha es en formato dd/mm/yyyy y ha de ser actual o futura";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
