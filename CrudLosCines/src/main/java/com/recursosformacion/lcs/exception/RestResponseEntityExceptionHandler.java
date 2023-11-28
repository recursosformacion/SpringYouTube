package com.recursosformacion.lcs.exception;

import java.nio.file.AccessDeniedException;
import java.util.Arrays;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@CrossOrigin
@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

	String MI_RUTA = "com.recursosformacion.lcs";
	int MI_RUTA_LENGTH = MI_RUTA.length();

	
	@ExceptionHandler(value = { 
			DomainException.class, 
			DAOException.class, 
			ControllerException.class,
			IllegalArgumentException.class, 
			IllegalStateException.class,
			jakarta.validation.ConstraintViolationException.class,
			org.springframework.dao.DuplicateKeyException.class,
			org.springframework.web.HttpRequestMethodNotSupportedException.class,
			org.springframework.web.bind.MethodArgumentNotValidException.class,
			org.springframework.web.bind.MissingRequestHeaderException.class,
			org.springframework.web.bind.MissingServletRequestParameterException.class,
			org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class,
			org.springframework.http.converter.HttpMessageNotReadableException.class })
	@ResponseBody
	public ResponseEntity<Map<String, Object>> handleConflict(Exception ex) {
		String mensaje = ex.getClass().getSimpleName() + "-" + ex.getMessage();
		//mensaje = "{" + mensaje.replace(";","},{")+"}";
		System.out.println("mensaje0-" + mensaje);
		List<String> m1 = Arrays.asList(mensaje.split(";"));
		return montaError(ex, m1, HttpStatus.BAD_REQUEST);
	}

//	@ExceptionHandler({ AccessDeniedException.class })
//    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(
//      Exception ex, WebRequest request) {
//		Map<String, Object> map = new LinkedHashMap<String, Object>();
//		String miPaquete = this.getClass().getPackageName();
//		map.clear();
//		map.put("status", 0);
//		map.put("message", "Error al acceder a zona protegida");
//        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.FORBIDDEN);
//    }

	private ResponseEntity<Map<String, Object>> montaError(Exception ex, List<String> mensaje, HttpStatus conflict) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		String miPaquete = this.getClass().getPackageName();
		System.out.println(miPaquete);
		/* *************************************************************************
		 * obtener paquete base de spring.....
		 */
		map.clear();
		map.put("status", 0);
		map.put("message", mensaje);
		// Array de objetos
		StackTraceElement[] ste = ex.getStackTrace();
		StackTraceElement[] stack = Arrays.stream(ste)
				//.peek(s->System.out.println(s.getClassName()))
				.filter(s ->  s.getClassName().length() < MI_RUTA_LENGTH ? false :  s.getClassName().substring(0, MI_RUTA_LENGTH).equals(MI_RUTA))
				.toArray(StackTraceElement[]::new);
		map.put("stacktrace", stack);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
	}
}
