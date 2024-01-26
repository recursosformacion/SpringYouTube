package com.recursosformacion.lcs.model_dto;

public class CineProjectionNombre {
	
	private Long id_cine;
	private String ci_nombre;
	private String ci_barrio;
	public CineProjectionNombre(Long id_cine, String ci_nombre, String ci_barrio) {
		super();
		this.id_cine = id_cine;
		this.ci_nombre = ci_nombre;
		this.ci_barrio = ci_barrio;
	}
	public Long getId_cine() {
		return id_cine;
	}
	public String getCi_nombre() {
		return ci_nombre;
	}
	public String getCi_barrio() {
		return ci_barrio;
	}
	
}
