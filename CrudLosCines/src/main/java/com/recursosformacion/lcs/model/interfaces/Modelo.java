package com.recursosformacion.lcs.model.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Transient;



public interface Modelo {
	
	@Transient
	@JsonIgnore
	public boolean isValidInsert();
	@Transient
	@JsonIgnore
	public boolean isValidUpdate();

}
