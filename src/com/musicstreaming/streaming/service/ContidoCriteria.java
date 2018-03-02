package com.musicstreaming.services;

import com.musicstreaming.model.Contido;

public class ContidoCriteria extends Contido {
		
	private String [] tipos = null;
	
	public ContidoCriteria() {
	}

	public String[] getTipos() {
		return tipos;
	}

	public void setTipos(String[] tipos) {
		this.tipos = tipos;
	}
	
	
}
