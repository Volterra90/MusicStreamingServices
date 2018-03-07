package com.musicstreaming.streaming.model;

public class Contido {
	
	//Atributos
	private Long codContido = null;
	private String nome = null;
	private Character tipo = null;
	private Long codArtista = null;
	private Long codEstilo = null;
	private Integer media = null;
	
	public Contido() {}
	
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
	public Long getCodArtista() {
		return codArtista;
	}
	public void setCodArtista(Long codArtista) {
		this.codArtista = codArtista;
	}
	public Long getCodEstilo() {
		return codEstilo;
	}
	public void setCodEstilo(Long codEstilo) {
		this.codEstilo = codEstilo;
	}

	public Integer getMedia() {
		return media;
	}

	public void setMedia(Integer media) {
		this.media = media;
	}
	

}
