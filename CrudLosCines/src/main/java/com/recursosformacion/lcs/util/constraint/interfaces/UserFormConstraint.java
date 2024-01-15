package com.recursosformacion.lcs.util.constraint.interfaces;

import java.lang.annotation.ElementType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

import com.recursosformacion.lcs.util.constraint.validator.UserFormConstraintValidator;

@Constraint(validatedBy = UserFormConstraintValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)

public @interface UserFormConstraint {
	String message() default "Datos de Entrada erroneos";

    String field();

    String fieldMatch();

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
    	UserFormConstraint[] value();
    }
}
