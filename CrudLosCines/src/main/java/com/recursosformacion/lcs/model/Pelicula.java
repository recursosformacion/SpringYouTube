package com.recursosformacion.lcs.model;

import com.recursosformacion.lcs.model.interfaces.Modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pelicula implements Modelo {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id_pelicula;
	private String pe_titulo;
	private int pe_identificador;
	public Pelicula() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Pelicula(long id_pelicula, String pe_titulo, int pe_identificador) {
		super();
		this.id_pelicula = id_pelicula;
		this.pe_titulo = pe_titulo;
		this.pe_identificador = pe_identificador;
	}
	public long getId_pelicula() {
		return id_pelicula;
	}
	public void setId_pelicula(long id_pelicula) {
		this.id_pelicula = id_pelicula;
	}
	public String getPe_titulo() {
		return pe_titulo;
	}
	public void setPe_titulo(String pe_titulo) {
		this.pe_titulo = pe_titulo;
	}
	public int getPe_identificador() {
		return pe_identificador;
	}
	public void setPe_identificador(int pe_identificador) {
		this.pe_identificador = pe_identificador;
	}
	@Override
	public String toString() {
		return "Pelicula [id_pelicula=" + id_pelicula + ", pe_titulo=" + pe_titulo + ", pe_identificador="
				+ pe_identificador + "]";
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
