package com.musicstreaming.streaming.service;

import com.musicstreaming.streaming.model.Contido;

public class ContidoCriteria extends Contido {
		
	private Character [] tipos = null;
	
	public ContidoCriteria() {
	}

	public Character[] getTipos() {
		return tipos;
	}

	public void setTipos(Character[] tipos) {
		this.tipos = tipos;
	}
	
	
}
