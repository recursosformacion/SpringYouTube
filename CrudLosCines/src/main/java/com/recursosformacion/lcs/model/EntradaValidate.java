package com.recursosformacion.lcs.model;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.recursosformacion.lcs.util.Rutinas;

@Component
public class EntradaValidate implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		
		return Entrada.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Entrada ent = (Entrada) target;
		ValidationUtils.rejectIfEmpty(errors, "ent_fila", "required.ent_fila", "No se acepta fila vacia");
		if (ent.getEnt_fila()<1 || ent.getEnt_fila()>100) {
			errors.reject("Se ha indicado un numero de fila erroneo");
		}
		ValidationUtils.rejectIfEmpty(errors, "ent_numero", "required.ent_numero", "No se acepta numero vacio");
		if (ent.getEnt_numero()<1 || ent.getEnt_numero()>100) {
			errors.reject("Se ha indicado un numero de asiento erroneo");
		}
		
		if (!Rutinas.cumpleDNI(ent.getIdCliente())) {
			errors.reject("Numero de DNI erroneo");
		}
		
	}

}
