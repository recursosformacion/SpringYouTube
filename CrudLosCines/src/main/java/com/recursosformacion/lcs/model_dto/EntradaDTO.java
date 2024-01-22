package com.recursosformacion.lcs.model_dto;


import org.springframework.stereotype.Component;

import com.recursosformacion.lcs.util.constraint.interfaces.CheckCineValidation;
import com.recursosformacion.lcs.util.constraint.interfaces.CheckFechaFuturaValidation;
import com.recursosformacion.lcs.util.constraint.interfaces.DniConstraint;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;



@Component
public class EntradaDTO {

	
	private Long id_entrada;
	
	@NotNull
	@CheckFechaFuturaValidation
	private String ent_fecha;
	
	@NotNull(message = "Es necesario indicar el cine")
	@CheckCineValidation
	private Long entCine;
	
	@NotNull(message = "Es necesario indicar la fila")
	@Min(value = 1, message = "Se ha de indicar una fila mayor que 0")
    @Max(value = 100, message = "Se ha de indicar una fila inferior a 100")
	private int ent_fila;
	
	@NotNull(message = "Es necesario indicar el asiento")
	@Min(value = 1, message = "Se ha de indicar un asiento mayor que 0")
    @Max(value = 100, message = "Se ha de indicar un asiento inferior a 100")
	private int ent_numero;

	@NotBlank
	@Size(message = "Error en el identificador del cliente ¿DNI? '${validatedValue}' .Su longitud debe ser {min}",
			  max = 12, min = 12)
	@DniConstraint
	private String idCliente;
	

	
	
	public EntradaDTO() {
		super();
	}
	
	public EntradaDTO(Long id_entrada, String ent_fecha, Long id_cine, int ent_numero, String idCliente) {
		super();
		this.id_entrada = id_entrada;
		this.ent_fecha = ent_fecha;
		this.entCine = id_cine;
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
		return entCine;
	}
	public void setId_cine(Long id_cine) {
		this.entCine = id_cine;
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
	
	public Long getEntCine() {
		return entCine;
	}
	public void setEntCine(Long entCine) {
		this.entCine = entCine;
	}
	public int getEnt_fila() {
		return ent_fila;
	}
	public void setEnt_fila(int ent_fila) {
		this.ent_fila = ent_fila;
	}
	@Override
	public String toString() {
		return "EntradaDTO [id_entrada=" + id_entrada + ", ent_fecha=" + ent_fecha + ", id_cine=" + entCine
				+ ", ent_numero=" + ent_numero + ", idCliente=" + idCliente + "]";
	}
	
	
}
