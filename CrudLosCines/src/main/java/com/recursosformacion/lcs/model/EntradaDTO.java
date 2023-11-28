package com.recursosformacion.lcs.model;

import java.time.LocalDate;

import com.recursosformacion.lcs.model.interfaces.Modelo;
import com.recursosformacion.lcs.util.Constantes;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


public class EntradaDTO implements Modelo {

	private Long id_entrada;
	
	private String ent_fecha_str;
	
	private int ent_fila;
	
	private int ent_numero;
	
	private String idCliente;
	 
	private Long ent_cine;
	
	
	public EntradaDTO() {
		super();
	}
	
	

	public EntradaDTO(long id_entrada, String ent_fecha_str,  Long ent_cine, int ent_fila, int ent_numero) {
		super();
		setId_entrada(id_entrada);
		setEnt_fecha_str(ent_fecha_str);
		setEnt_cine(ent_cine);
		setEnt_numero(ent_numero);
		setEnt_fila(ent_fila);
	}
	
	public long getId_entrada() {
		return id_entrada;
	}
	public void setId_entrada(long id_entrada) {
		this.id_entrada = id_entrada;
	}
	public String getEnt_fecha_str() {
		return ent_fecha_str;
	}
	public void setEnt_fecha_str(String ent_fecha_str) {
		this.ent_fecha_str = ent_fecha_str;
	}
	
	public int getEnt_numero() {
		return ent_numero;
	}
	public void setEnt_numero(int ent_numero) {
		this.ent_numero = ent_numero;
	}
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public int getEnt_fila() {
		return ent_fila;
	}
	public void setEnt_fila(int i) {
		this.ent_fila = i;
	}
	
	public Long getEnt_cine() {
		return ent_cine;
	}
	public void setEnt_cine(Long ent_cine) {
		this.ent_cine = ent_cine;
	}
	
	@Override
	public String toString() {
		return "Entrada [id_entrada=" + id_entrada + ", ent_fecha=" + ent_fecha_str + ", Cine=" + ent_cine + ", ent_numero="
				+ ent_numero + ", idCliente=" + idCliente + "]";
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
