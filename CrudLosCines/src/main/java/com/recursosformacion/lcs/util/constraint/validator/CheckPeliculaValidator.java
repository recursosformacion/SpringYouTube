package com.recursosformacion.lcs.util.constraint.validator;

import com.recursosformacion.lcs.service.PeliculaService;
import com.recursosformacion.lcs.util.constraint.interfaces.CheckPeliculaValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckPeliculaValidator implements ConstraintValidator<CheckPeliculaValidation, Long>{

	private final PeliculaService peliculaService;
		
	CheckPeliculaValidator(PeliculaService peliculaService){
		this.peliculaService=peliculaService;
	}

	@Override
	public boolean isValid(Long pelicula, ConstraintValidatorContext context) {
		if (pelicula == null) return true;
		return peliculaService.existe(pelicula);
	}
}
