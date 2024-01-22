package com.recursosformacion.lcs.exception;

import java.util.Arrays;

import java.util.LinkedHashMap;
import java.util.Map;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@CrossOrigin
@ControllerAdvice
public class RestResponseEntityExceptionHandler {

	String MI_RUTA = "com.recursosformacion.lcs";
	int MI_RUTA_LENGTH = MI_RUTA.length();

	
	@ExceptionHandler(value = { 
			DomainException.class, 
			DAOException.class, 
			ControllerException.class,
			IllegalArgumentException.class, 
			IllegalStateException.class,
			jakarta.validation.UnexpectedTypeException.class,
			org.springframework.dao.DuplicateKeyException.class,
			org.springframework.web.HttpRequestMethodNotSupportedException.class,
			org.springframework.web.bind.MethodArgumentNotValidException.class,
			org.springframework.web.bind.MissingRequestHeaderException.class,
			org.springframework.web.bind.MissingServletRequestParameterException.class,
			org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class,
			java.lang.ArithmeticException.class,
			org.springframework.http.converter.HttpMessageNotReadableException.class })
	@ResponseBody
	public ResponseEntity<Map<String, Object>> handleConflict(Exception ex) {
		String mensaje = ex.getClass().getSimpleName() + "-" + ex.getMessage();
		System.out.println(mensaje);
		return montaError(ex, mensaje, HttpStatus.BAD_REQUEST);
	}

//	@ExceptionHandler(ControllerException.class)
//	public ResponseEntity<Object> procControllerException(final Exception e) {
//		String mensaje = e.getClass() + "-" + e.getMessage();
//		System.out.println(mensaje);
//		return montaError(e, mensaje, HttpStatus.BAD_REQUEST);
//	}

	private ResponseEntity<Map<String, Object>> montaError(Exception ex, String mensaje, HttpStatus conflict) {
//*********inicializacion************************************************************
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		String miPaquete = this.getClass().getPackageName();
		System.out.println(miPaquete);
//********* Estableciendo el status de salida y el mensaje de error
		
		map.clear();
		map.put("status", 0);
		map.put("message", mensaje);
		
		/* *************************************************************************	
		// getStackTrace se deberia filtrar por "className": "es.rf.tienda.*/
		// Array de objetos
		StackTraceElement[] ste = ex.getStackTrace();
		StackTraceElement[] stack = Arrays.stream(ste)
				.peek(s->System.out.println(s.getClassName()))
				.filter(s ->  s.getClassName().length() < MI_RUTA_LENGTH ? false :  s.getClassName().substring(0, MI_RUTA_LENGTH).equals(MI_RUTA))
				.toArray(StackTraceElement[]::new);
		map.put("stacktrace", stack);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
	}
}