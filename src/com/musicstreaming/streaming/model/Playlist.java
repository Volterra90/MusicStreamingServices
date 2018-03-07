package com.musicstreaming.streaming.model;

public class Playlist extends GrupoCancions{
	
	Long codUsuario = null;
	
	public Playlist() {
		
	}

	public Long getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(Long codUsuario) {
		this.codUsuario = codUsuario;
	}
	
	
}
