package com.musicstreaming.streaming.model;

public class Artista{

	//Atributos
	private Long codArtista = null;
	private String nomeArtista = null;
	
	
	public Artista() {}
	//Getters & Setters	

	public Long getCodArtista() {
		return codArtista;
	}

	public void setCodArtista(Long codArtista) {
		this.codArtista = codArtista;
	}

	public String getNomeArtista() {
		return nomeArtista;
	}

	public void setNomeArtista(String nomeArtista) {
		this.nomeArtista = nomeArtista;
	}
	
	
}
