package com.recursosformacion.lcs.util;

import java.time.format.DateTimeFormatter;



public class Constantes {

	//valores del JSON de respuesta
	public final static String STATUS = "status";
	public final static String MENSAJE = "mensaje";
	public final static String DATOS = "data";
		
	public final static String FORMATO_FECHA = "dd/MM/yyyy";
	public final static DateTimeFormatter FORMATO_FECHA_EU =  DateTimeFormatter.ofPattern(FORMATO_FECHA);
}
