package com.recursosformacion.lcs.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

//Optimizacion en 
//https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
@RestControllerAdvice
public class ControllerExceptionValidation /*extends ResponseEntityExceptionHandler*/ {


	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public Map<String, Object> gestionValidaciones(MethodArgumentNotValidException ex) {
		Map<String, Object> errores = new HashMap<String, Object>();
		errores.put("status", 900);
		Map<String, String> listaErrores = new HashMap<String, String>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String nombreCampo = ((FieldError) error).getField();
			String mensaje = error.getDefaultMessage();
			listaErrores.put(nombreCampo, mensaje);
		});
		errores.put("lista", listaErrores);
		return errores;
	}

	@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(value = { jakarta.validation.ConstraintViolationException.class,
			org.hibernate.exception.ConstraintViolationException.class })
	public Map<String, Object> gestionConstraint(ConstraintViolationException ex) {
		Map<String, Object> errores = new HashMap<String, Object>();
		errores.put("status", 900);

		Map<String, String> listaErrores = new HashMap<String, String>();
		ex.getConstraintViolations().forEach(error -> {
			String nombreCampo = error.getPropertyPath().toString();
			String mensaje = error.getMessage();
			listaErrores.put(nombreCampo, mensaje);
		});
		errores.put("lista", listaErrores);
		return errores;
	}

}
