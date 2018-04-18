package com.musicstreaming.streaming.service;

import com.musicstreaming.streaming.model.Contido;

public class ContidoCriteria extends Contido {
		
	private Character [] tipos = null;
	private String nomeArtista = null;
	
	public ContidoCriteria() {
	}

	public Character[] getTipos() {
		return tipos;
	}

	public void setTipos(Character[] tipos) {
		this.tipos = tipos;
	}

	public String getNomeArtista() {
		return nomeArtista;
	}

	public void setNomeArtista(String nomeArtista) {
		this.nomeArtista = nomeArtista;
	}
	
}
