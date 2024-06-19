package com.recursosformacion.lcs.util.constraint.validator;

import com.recursosformacion.lcs.service.CineService;
import com.recursosformacion.lcs.util.constraint.interfaces.CheckCineValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckCineValidator implements ConstraintValidator<CheckCineValidation, Long>{

	private final CineService cineService;
		
	CheckCineValidator(CineService cineService){
		this.cineService=cineService;
	}

	@Override
	public boolean isValid(Long cine, ConstraintValidatorContext context) {
		if (cine == null) return true;
		return cineService.existe(cine);
	}
}
