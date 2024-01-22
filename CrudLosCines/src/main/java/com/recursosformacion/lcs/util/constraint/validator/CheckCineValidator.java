package com.recursosformacion.lcs.util.constraint.validator;

import com.recursosformacion.lcs.repository.ICine;
import com.recursosformacion.lcs.util.constraint.interfaces.CheckCineValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckCineValidator implements ConstraintValidator<CheckCineValidation, Long>{

	private final ICine cineRepository;
		
	CheckCineValidator(ICine cineRepository){
		this.cineRepository=cineRepository;
	}

	@Override
	public boolean isValid(Long cine, ConstraintValidatorContext context) {
		if (cine == null) return true;
		return cine != null &&  cineRepository.existsById(cine);
	}
}
