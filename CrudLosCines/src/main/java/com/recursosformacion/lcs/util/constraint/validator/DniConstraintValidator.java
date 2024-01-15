package com.recursosformacion.lcs.util.constraint.validator;

import com.recursosformacion.lcs.util.Rutinas;
import com.recursosformacion.lcs.util.constraint.interfaces.DniConstraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DniConstraintValidator implements ConstraintValidator<DniConstraint, String>  {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {		
		return value != null && Rutinas.cumpleDNI(value);
	}

	

}
