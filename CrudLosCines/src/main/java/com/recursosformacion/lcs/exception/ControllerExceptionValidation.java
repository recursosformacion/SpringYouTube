package com.recursosformacion.lcs.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionValidation extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public Map<String,Object> gestionValidaciones(MethodArgumentNotValidException ex) {
		Map<String,Object> errores = new HashMap<>();
		errores.put("status",900);
		Map<String,String> listaErrores = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String nombreCampo = ((FieldError) error).getField();
			String mensaje = error.getDefaultMessage();
			listaErrores.put(nombreCampo,mensaje);
		});
		errores.put("lista",listaErrores);
		return errores;
	}

	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<Object> handleMethodArgumentNotValid(Exception ex, WebRequest request) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
