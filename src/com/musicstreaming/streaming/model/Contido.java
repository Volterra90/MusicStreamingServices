package com.musicstreaming.model;

public abstract class Contido {
	
	//Atributos
	private Long codContido = null;
	private String nome = null;
	private char tipo = null;
	private Long duracion = null;
	private Long codAutor = null;
	private Long codEstilo = null;
	
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
	public char getTipo() {
		return tipo;
	}
	public void setTipo(char tipo) {
		this.tipo = tipo;
	}
	public Long getDuracion() {
		return duracion;
	}
	public void setDuracion(Long duracion) {
		this.duracion = duracion;
	}
	public Long getCodAutor() {
		return codAutor;
	}
	public void setCodAutor(Long codAutor) {
		this.codAutor = codAutor;
	}
	public Long getCodEstilo() {
		return codEstilo;
	}
	public void setCodEstilo(Long codEstilo) {
		this.codEstilo = codEstilo;
	}
	

}
