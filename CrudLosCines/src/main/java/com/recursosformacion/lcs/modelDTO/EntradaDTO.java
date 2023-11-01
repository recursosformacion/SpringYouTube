package com.recursosformacion.lcs.modelDTO;

import org.springframework.stereotype.Component;



@Component
public class EntradaDTO {

	
	private Long id_entrada;	
	private String ent_fecha;
	private Long ent_cine;
	private int ent_fila;
	private int ent_numero;
	private String idCliente;
	
	public EntradaDTO() {
		super();
	}
	public EntradaDTO(Long id_entrada, String ent_fecha, Long id_cine, int ent_numero, String idCliente) {
		super();
		this.id_entrada = id_entrada;
		this.ent_fecha = ent_fecha;
		this.ent_cine = id_cine;
		this.ent_numero = ent_numero;
		this.idCliente = idCliente;
	}
	public Long getId_entrada() {
		return id_entrada;
	}
	public void setId_entrada(Long id_entrada) {
		this.id_entrada = id_entrada;
	}
	public String getEnt_fecha() {
		return ent_fecha;
	}
	public void setEnt_fecha(String ent_fecha) {
		this.ent_fecha = ent_fecha;
	}
	public Long getId_cine() {
		return ent_cine;
	}
	public void setId_cine(Long id_cine) {
		this.ent_cine = id_cine;
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
	
	public Long getEnt_cine() {
		return ent_cine;
	}
	public void setEnt_cine(Long ent_cine) {
		this.ent_cine = ent_cine;
	}
	public int getEnt_fila() {
		return ent_fila;
	}
	public void setEnt_fila(int ent_fila) {
		this.ent_fila = ent_fila;
	}
	@Override
	public String toString() {
		return "EntradaDTO [id_entrada=" + id_entrada + ", ent_fecha=" + ent_fecha + ", id_cine=" + ent_cine
				+ ", ent_numero=" + ent_numero + ", idCliente=" + idCliente + "]";
	}
	
	
}
