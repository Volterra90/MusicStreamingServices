package com.musicstreaming.streaming.model;

public class Cancion extends Contido {
	
	private Long duracion = null;
	
	public Cancion() {
		setTipo(TipoContido.CANCION);
	}
	
	public Long getDuracion() {
		return duracion;
	}
	
	public void setDuracion(Long duracion) {
		this.duracion = duracion;
	}
	
	 
	
}
