package com.musicstreaming.streaming.model;

public class Direccion {
	
	//Atributos
	private Long codDireccion = null;
	private String direccion = null;
	private Long codProvincia = null;
	private Long codUsuario = null;
	
	//Getters & Setters
	public Long getCodDireccion() {
		return codDireccion;
	}
	public void setCodDireccion(Long codDireccion) {
		this.codDireccion = codDireccion;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Long getCodProvincia() {
		return codProvincia;
	}
	public void setCodProvincia(Long codProvincia) {
		this.codProvincia = codProvincia;
	}
	public Long getCodUsuario() {
		return codUsuario;
	}
	public void setCodUsuario(Long codUsuario) {
		this.codUsuario = codUsuario;
	}
	
	
}
