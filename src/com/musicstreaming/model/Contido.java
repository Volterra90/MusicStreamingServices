package com.musicstreaming.model;

import java.util.Date;

public abstract class Contido {
	
	//Atributos
	private Long codContido = null;
	private String nome = null;
	private String tipo = null;
	private Date duracion = null;
	private Long codAutor = null;
	
	//Getters & Setters
	public Long getCodContido() {
		return codContido;
	}
	public void setCodContido(Long codContido) {
		this.codContido = codContido;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Date getDuracion() {
		return duracion;
	}
	public void setDuracion(Date duracion) {
		this.duracion = duracion;
	}
	public Long getCodAutor() {
		return codAutor;
	}
	public void setCodAutor(Long codAutor) {
		this.codAutor = codAutor;
	}
	
	

}
