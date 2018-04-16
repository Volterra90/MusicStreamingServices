package com.musicstreaming.streaming.model;

public class Cancion extends Contido {
	
	private Integer duracion = null;
	
	public Cancion() {
		setTipo(TipoContido.CANCION);
	}
	
	public Integer getDuracion() {
		return duracion;
	}
	
	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}
	
	 
	
}
