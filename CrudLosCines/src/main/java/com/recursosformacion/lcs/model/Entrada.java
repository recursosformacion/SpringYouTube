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


@Entity
@Table(name = "Entrada")
public class Entrada implements Modelo {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id_entrada;
	
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@Column(nullable=false,columnDefinition = "DATE")
	private LocalDate ent_fecha;
	
	@Column(nullable=false)
	private int ent_fila;
	
	@Column(nullable=false)
	private int ent_numero;
	
	@Column(nullable=false)
	private String idCliente;
	
	@Column(nullable=false)
	private Long ent_cine;
	

	public Entrada() {
		super();
	}
	public Entrada(long id_entrada, LocalDate ent_fecha, Long ent_cine, int ent_fila, int ent_numero) {
		super();
		setId_entrada(id_entrada);
		setEnt_fecha(ent_fecha);
		setEnt_cine(ent_cine);
		setEnt_numero(ent_numero);
		setEnt_fila(ent_fila);
	}
	

	public Entrada(long id_entrada, String ent_fecha_str,  Long ent_cine, int ent_fila, int ent_numero) {
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
	public LocalDate getEnt_fecha() {
		return ent_fecha;
	}
	public void setEnt_fecha(LocalDate ent_fecha) {
		this.ent_fecha = ent_fecha;
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

	public void setEnt_fecha_str(String ent_fecha_str) {		
		setEnt_fecha(LocalDate.parse(ent_fecha_str,Constantes.FORMATO_FECHA_EU));	
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
		return "Entrada [id_entrada=" + id_entrada + ", ent_fecha=" + ent_fecha + ", Cine=" + ent_cine + ", ent_numero="
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
