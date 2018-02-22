package com.musicstreaming.model;

import java.util.Date;

public class Usuario extends Autor {
	
	//Atributos
	private Date fechaSubscricion = null;
	private String xenero = null;
	private Date fechaNacemento = null;
	private String email = null;
	private String contrasinal = null;
	private String nome = null;
	private String apelidos = null;
	private Long codDireccion = null;
	
	//Getters & Setters
	public Date getFechaSubscricion() {
		return fechaSubscricion;
	}
	public void setFechaSubscricion(Date fechaSubscricion) {
		this.fechaSubscricion = fechaSubscricion;
	}
	public String getXenero() {
		return xenero;
	}
	public void setXenero(String xenero) {
		this.xenero = xenero;
	}
	public Date getFechaNacemento() {
		return fechaNacemento;
	}
	public void setFechaNacemento(Date fechaNacemento) {
		this.fechaNacemento = fechaNacemento;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContrasinal() {
		return contrasinal;
	}
	public void setContrasinal(String contrasinal) {
		this.contrasinal = contrasinal;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getApelidos() {
		return apelidos;
	}
	public void setApelidos(String apelidos) {
		this.apelidos = apelidos;
	}
	public Long getCodDireccion() {
		return codDireccion;
	}
	public void setCodDireccion(Long codDireccion) {
		this.codDireccion = codDireccion;
	}
	
	

}
