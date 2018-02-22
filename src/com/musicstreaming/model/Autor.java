package com.musicstreaming.model;

public abstract class Autor {
	
	private Long codAutor = null;
	private String nomeAutor = null;
	
	public Long getCodAutor() {
		return codAutor;
	}
	public void setCodAutor(Long codAutor) {
		this.codAutor = codAutor;
	}
	public String getNomeAutor() {
		return nomeAutor;
	}
	public void setNomeAutor(String nomeAutor) {
		this.nomeAutor = nomeAutor;
	}
	

}
