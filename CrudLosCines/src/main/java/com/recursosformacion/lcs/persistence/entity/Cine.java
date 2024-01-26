package com.recursosformacion.lcs.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import com.recursosformacion.lcs.persistence.entity.interfaces.Modelo;
import com.recursosformacion.lcs.util.Rutinas;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Cine")
public class Cine implements Modelo {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id_cine;

	@Column(nullable = false, length = 50)
	private String ci_nombre;

	@Column(nullable = true, length = 100)
	private String ci_calle;

	@Column(nullable = true, length = 100)
	private String ci_barrio;

	@Column(nullable = false)
	private int ci_capacidad;
	
	@ElementCollection
	private List<Long> ci_lista_entradas;

	
	

	public Cine() {
		super();
	}

	public Cine(long id_cine, String ci_nombre, int ci_capacidad, List<Long> ci_lista_entradas) {
		super();
		this.id_cine = id_cine;
		this.ci_nombre = ci_nombre;
		this.ci_capacidad = ci_capacidad;
		setCi_lista_entradas(ci_lista_entradas);
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
		return "Cine [id_cine=" + id_cine + ", ci_nombre=" + ci_nombre + ", ci_capacidad=" + ci_capacidad + "]";
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

	@Override
	public boolean isValidInsert() {
		return true;
	}

	@Override
	public boolean isValidUpdate() {
		return true;
	}
}
