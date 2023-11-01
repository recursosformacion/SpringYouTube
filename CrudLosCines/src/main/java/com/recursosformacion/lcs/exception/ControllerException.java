package com.recursosformacion.lcs.exception;

@SuppressWarnings("serial")
public class ControllerException extends Exception {
	
	public ControllerException() {
	}

	public ControllerException(String mensaje) {
		super(mensaje);

	}
}
