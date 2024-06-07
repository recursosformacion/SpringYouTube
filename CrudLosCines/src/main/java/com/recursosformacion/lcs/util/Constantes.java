package com.recursosformacion.lcs.util;

import java.time.format.DateTimeFormatter;



public class Constantes {

	//valores del JSON de respuesta
	public static final String STATUS = "status";
	public static final String MENSAJE = "mensaje";
	public static final String DATOS = "data";
		
	public static final String FORMATO_FECHA = "dd/MM/yyyy";
	public static final DateTimeFormatter FORMATO_FECHA_EU =  DateTimeFormatter.ofPattern(FORMATO_FECHA);
	
	public static final String MSJ_NO_EXISTEN_DATOS = "No existen datos";
	public static final String MSJ_INSERCION_OK = "Insercion realizada";
	public static final String MSJ_ACTUALIZACION_OK = "Actualizacion realizada";
	public static final String MSJ_ELIMINACION_OK = "Eliminacion realizada";
	public static final String MSJ_ERROR_DELETE = "Error al hacer la eliminacion";
	public static final String MSJ_ERROR_UPDATE = "Error al hacer la actualizacion";
	public static final String MSJ_ERROR_INSERT = "Error al hacer la insercion";
	public static final String MSJ_ERROR_CINE_N = "No existe el cine indicado, id:";
	public static final String MSJ_ERROR_PELICULA_N = "No existe la pelicula indicada, id:";
}
