package com.musicstreaming.model;

import java.util.Date;

public class Album extends GrupoCancions {

	//Atributos
	private Date fechaPublicacion = null;
	private String nomeDiscografica = null;
	
	//Getters & Setters
	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}
	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}
	public String getNomeDiscografica() {
		return nomeDiscografica;
	}
	public void setNomeDiscografica(String nomeDiscografica) {
		this.nomeDiscografica = nomeDiscografica;
	}
	
	
}