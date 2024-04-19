package com.recursosformacion.lcs.persistence.entity;

import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


public class CineTest  extends Cine{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_cine;

	
	public CineTest(long id_cine, String ci_nombre, String ci_calle, int ci_capacidad) {
		super( id_cine,  ci_nombre,  ci_calle,  ci_capacidad);
		
		
	}

	public CineTest(Long id_cine, String ci_nombre,  String ci_calle, String ci_barrio,
			 int ci_capacidad, List<Long> ci_lista_entradas) {
		super(id_cine, ci_nombre, ci_calle,  ci_barrio,	ci_capacidad, ci_lista_entradas);
		
	}
}