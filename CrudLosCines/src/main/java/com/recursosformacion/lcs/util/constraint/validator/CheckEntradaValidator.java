package com.recursosformacion.lcs.util.constraint.validator;

import com.recursosformacion.lcs.service.EntradaService;
import com.recursosformacion.lcs.util.constraint.interfaces.CheckEntradaValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckEntradaValidator implements ConstraintValidator<CheckEntradaValidation, Long>{

	private final EntradaService entradaService;
		
	CheckEntradaValidator(EntradaService entradaService){
		this.entradaService=entradaService;
	}

	@Override
	public boolean isValid(Long entrada, ConstraintValidatorContext context) {
		if (entrada == null) return true;
		return entradaService.existsById(entrada);
	}
}
