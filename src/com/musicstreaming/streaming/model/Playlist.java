package com.musicstreaming.streaming.model;

public class Playlist extends GrupoCancions{
	
	private Long codUsuario = null;
	
	public Playlist() {
		setTipo(TipoContido.PLAYLIST);
	}

	public Long getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(Long codUsuario) {
		this.codUsuario = codUsuario;
	}
	
	
}
