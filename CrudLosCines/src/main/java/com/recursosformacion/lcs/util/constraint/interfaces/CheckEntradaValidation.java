package com.recursosformacion.lcs.util.constraint.interfaces;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.recursosformacion.lcs.util.Constantes;
import com.recursosformacion.lcs.util.constraint.validator.CheckEntradaValidator;

import java.lang.annotation.RetentionPolicy;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

import jakarta.validation.Payload;
import jakarta.validation.Constraint;

@Target( { FIELD, PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CheckEntradaValidator.class)
public @interface CheckEntradaValidation {
    public String message() default Constantes.MSJ_ERROR_ENTRADA_SN;
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}