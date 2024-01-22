package com.recursosformacion.lcs.util.constraint.validator;

import java.time.LocalDate;

import com.recursosformacion.lcs.util.Rutinas;
import com.recursosformacion.lcs.util.constraint.interfaces.CheckFechaFuturaValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckFechaFuturaValidator implements ConstraintValidator<CheckFechaFuturaValidation, String> {

	final String FECHA_FORMATO_ERRONEO = "La fecha debe tener un formato dd/mm/aaaa";
	final String FECHA_INTERVALO = "La fecha ha de ser actual o futura (Nunca en el pasado)";
	
	@Override
	public boolean isValid(String fecha, ConstraintValidatorContext ctx) {
		if (null == fecha) {
			return true;
		}
		ctx.disableDefaultConstraintViolation();
		if (!Rutinas.esFechaValida(fecha)) {			
			ctx.buildConstraintViolationWithTemplate( FECHA_FORMATO_ERRONEO).addConstraintViolation();
			return false;
		}
		if (!Rutinas.isGreaterOrEqual(Rutinas.fechaToLocalDate(fecha), LocalDate.now())) {
			ctx.buildConstraintViolationWithTemplate( FECHA_INTERVALO).addConstraintViolation();
			return false;
		}
		return true;
	}

}
