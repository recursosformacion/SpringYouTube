package com.recursosformacion.lcs.persistence.entity.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Transient;



public interface Modelo<S > {
	
	@Transient
	@JsonIgnore
	public S getId();
	@Transient
	@JsonIgnore
	public void setId(S s);

	
	@Transient
	@JsonIgnore
	public boolean isValidInsert();
	@Transient
	@JsonIgnore
	public boolean isValidUpdate();
	

	

}
