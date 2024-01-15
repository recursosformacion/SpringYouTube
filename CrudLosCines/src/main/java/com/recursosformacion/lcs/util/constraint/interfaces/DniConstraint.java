package com.recursosformacion.lcs.util.constraint.interfaces;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import com.recursosformacion.lcs.util.constraint.validator.DniConstraintValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = DniConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DniConstraint {
    String message() default "DNI - valor falso";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
