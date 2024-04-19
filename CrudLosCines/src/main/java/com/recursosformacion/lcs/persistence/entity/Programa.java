package com.recursosformacion.lcs.persistence.entity;

import java.time.LocalDate;

import com.recursosformacion.lcs.persistence.entity.interfaces.Modelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Programa implements Modelo {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id_programa;

	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_pelicula")
	private Pelicula pelicula;

	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cine")
	private Cine cine;

	private LocalDate pr_inicio;
	private LocalDate pr_fin;

	public Programa() {
		super();

	}

	public Programa(long id_programa, Cine cine, Pelicula pelicula, LocalDate pr_inicio, LocalDate pr_fin) {
		super();
		this.id_programa = id_programa;
		this.pelicula = pelicula;
		this.cine = cine;
		this.pr_inicio = pr_inicio;
		this.pr_fin = pr_fin;
	}

	public long getId_programa() {
		return id_programa;
	}

	public void setId_programa(long id_programa) {
		this.id_programa = id_programa;
	}

	public Cine getCine() {
		return cine;
	}

	public void setCine(Cine cine) {
		this.cine = cine;
	}

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	public Pelicula getPelicula() {
		return pelicula;
	}

	
	public LocalDate getPr_inicio() {
		return pr_inicio;
	}

	public void setPr_inicio(LocalDate pr_inicio) {
		this.pr_inicio = pr_inicio;
	}

	public LocalDate getPr_fin() {
		return pr_fin;
	}

	public void setPr_fin(LocalDate pr_fin) {
		this.pr_fin = pr_fin;
	}

	@Override
	public String toString() {
		return "Programa [id_programa=" + id_programa + ", pelicula=" + pelicula + ", cine=" + cine + ", pr_inicio="
				+ pr_inicio + ", pr_fin=" + pr_fin + "]";
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
