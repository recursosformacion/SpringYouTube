package com.recursosformacion.lcs.persistence.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Usuario {

	//create the attributes of the entity
	@Id
	private Long id_user;
	private String usNombre;
	private String usApellido;
	private String usEmail;
	private String usPassword;
	private String rol;
	private String estado;
	private LocalDate usFecha_registro;
	private LocalDate usFecha_actualizacion;
	
	
	
	//create the constructor
	public Usuario() {
		super();
	}
	//create the constructor with parameters
	public Usuario(Long id_user, String usNombre, String usApellido, String usEmail, String usPassword, String rol,
			String estado, LocalDate usFecha_registro, LocalDate usFecha_actualizacion) {
		super();
		this.id_user = id_user;
		this.usNombre = usNombre;
		this.usApellido = usApellido;
		this.usEmail = usEmail;
		this.usPassword = usPassword;
		this.rol = rol;
		this.estado = estado;
		this.usFecha_registro = usFecha_registro;
		this.usFecha_actualizacion = usFecha_actualizacion;
	}
	
	public Long getId_user() {
		return id_user;
	}
	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}
	public String getUsNombre() {
		return usNombre;
	}
	public void setUsNombre(String usNombre) {
		this.usNombre = usNombre;
	}
	public String getUsApellido() {
		return usApellido;
	}
	public void setUsApellido(String usApellido) {
		this.usApellido = usApellido;
	}
	public String getUsEmail() {
		return usEmail;
	}
	public void setUsEmail(String usEmail) {
		this.usEmail = usEmail;
	}
	public String getUsPassword() {
		return usPassword;
	}
	public void setUsPassword(String usPassword) {
		this.usPassword = usPassword;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public LocalDate getUsFecha_registro() {
		return usFecha_registro;
	}
	public void setUsFecha_registro(LocalDate usFecha_registro) {
		this.usFecha_registro = usFecha_registro;
	}
	public LocalDate getUsFecha_actualizacion() {
		return usFecha_actualizacion;
	}
	public void setUsFecha_actualizacion(LocalDate usFecha_actualizacion) {
		this.usFecha_actualizacion = usFecha_actualizacion;
	}
	
	
	//create the getters and setters	
}
