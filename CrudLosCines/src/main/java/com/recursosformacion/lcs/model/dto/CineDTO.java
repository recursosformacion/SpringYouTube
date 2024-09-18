package com.recursosformacion.lcs.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recursosformacion.lcs.persistence.entity.interfaces.Modelo;
import com.recursosformacion.lcs.util.Rutinas;

import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;


public class CineDTO {

	
	private Long id_cine;

	@NotEmpty
	private String ci_nombre;

	@NotEmpty
	private String ci_calle;

	private String ci_barrio;

	@Positive
	@DecimalMax(value = "1000")
	private int ci_capacidad;
	
	@JsonIgnore
	@ElementCollection
	private List<Long> ci_lista_entradas;

	
	

	public CineDTO() {
		super();
	}

	public CineDTO(long id_cine, String ci_nombre, String ci_calle, int ci_capacidad) {
		super();
		this.id_cine = id_cine;
		this.ci_nombre = ci_nombre;
		this.ci_calle = ci_calle;
		this.ci_capacidad = ci_capacidad;
		
	}

	public CineDTO(Long id_cine, @NotEmpty String ci_nombre, @NotEmpty String ci_calle, String ci_barrio,
			@Positive @DecimalMax("1000") int ci_capacidad, List<Long> ci_lista_entradas) {
		super();
		this.id_cine = id_cine;
		this.ci_nombre = ci_nombre;
		this.ci_calle = ci_calle;
		this.ci_barrio = ci_barrio;
		this.ci_capacidad = ci_capacidad;
		if (ci_lista_entradas!=null)
			this.ci_lista_entradas = ci_lista_entradas;
	}

	public long getId_cine() {
		return id_cine;
	}

	public void setId_cine(long id_cine) {
		this.id_cine = id_cine;
	}

	public String getCi_nombre() {
		return ci_nombre;
	}

	public void setCi_nombre(String ci_nombre) {
		this.ci_nombre = ci_nombre;
	}

	public int getCi_capacidad() {
		return ci_capacidad;
	}

	public void setCi_capacidad(int ci_capacidad) {
		this.ci_capacidad = ci_capacidad;
	}

	
	
	@Override
	public String toString() {
		return "Cine [id_cine=" + id_cine + ", ci_nombre=" + ci_nombre + ", ci_calle=" + ci_calle + ", ci_barrio="
				+ ci_barrio + ", ci_capacidad=" + ci_capacidad + "]";
	}

	public String getCi_calle() {
		return ci_calle;
	}

	public void setCi_calle(String ci_calle) {
		this.ci_calle = ci_calle;
	}

	public String getCi_barrio() {
		return ci_barrio;
	}

	public void setCi_barrio(String ci_barrio) {
		this.ci_barrio = ci_barrio;
	}

	public List<Long> getCi_lista_entradas() {
		return ci_lista_entradas;
	}

	public void setCi_lista_entradas(List<Long> ci_lista_entradas) {
		if (Rutinas.isEmptyOrNull(ci_lista_entradas)) {
			ci_lista_entradas = new ArrayList<Long>();
		}
		this.ci_lista_entradas = ci_lista_entradas;
	}

	
	public Long getId() {
		return id_cine;
	}

	
	public void setId(Long t) {
		id_cine = t;		
	}


	
}
