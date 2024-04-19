package com.recursosformacion.lcs.persistence.entity;

import java.time.LocalDate;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


public class EntradaTestUso extends Entrada{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_entrada;
	
	public EntradaTestUso() {
		super();
	}
	public EntradaTestUso(
			long id_entrada, 
			LocalDate ent_fecha,  
			int ent_fila, 
			int ent_numero, 
			String idCliente, 
			Long entCine) {
		super(id_entrada, ent_fecha, ent_fila, ent_numero, idCliente, entCine);
	}
	
	public EntradaTestUso(
			long id_entrada, 
			String ent_fecha,  
			int ent_fila, 
			int ent_numero, 
			String idCliente, 
			Long entCine) {
		super(id_entrada, ent_fecha, ent_fila, ent_numero, idCliente, entCine);
	}
	
}

