package com.musicstreaming.streaming.model;

import java.util.Date;

public class Artista{

	//Atributos
	private Long codArtista = null;
	private Date anoFormacion = null;
	private String nomeArtista = null;
	
	
	public Artista() {}
	//Getters & Setters
	
	public Date getAnoFormacion() {
		return anoFormacion;
	}
	public void setAnoFormacion(Date anoFormacion) {
		this.anoFormacion = anoFormacion;
	}

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
