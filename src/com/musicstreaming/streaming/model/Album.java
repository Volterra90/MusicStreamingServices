package com.musicstreaming.streaming.model;

public class Album extends GrupoCancions {

	//Atributos
	private Integer fechaPublicacion = null;
	private String nomeDiscografica = null;

	
	public Album() {
		setTipo(TipoContido.ALBUM);
	}
	
	//Getters & Setters
	public Integer getFechaPublicacion() {
		return fechaPublicacion;
	}
	public void setFechaPublicacion(Integer fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}
	public String getNomeDiscografica() {
		return nomeDiscografica;
	}
	public void setNomeDiscografica(String nomeDiscografica) {
		this.nomeDiscografica = nomeDiscografica;
	}
	
}
