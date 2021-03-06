package com.musicstreaming.streaming.model;

import java.util.Date;

public class Usuario{
	
	//Atributos
	private Long idUsuario = null;
	private Date fechaSubscricion = null;
	private Character xenero = null;
	private Date fechaNacemento = null;
	private String email = null;
	private String contrasinal = null;
	private String nome = null;
	private String apelidos = null;
	private String nick = null;
	
	public Usuario() {
	}
	
	//Getters & Setters
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Date getFechaSubscricion() {
		return fechaSubscricion;
	}
	public void setFechaSubscricion(Date fechaSubscricion) {
		this.fechaSubscricion = fechaSubscricion;
	}
	public Character getXenero() {
		return xenero;
	}
	public void setXenero(Character xenero) {
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

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	

}
