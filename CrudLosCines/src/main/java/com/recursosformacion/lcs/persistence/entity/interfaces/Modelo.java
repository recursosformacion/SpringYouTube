package com.recursosformacion.lcs.persistence.entity.interfaces;

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
