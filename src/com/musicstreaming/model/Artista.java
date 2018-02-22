package com.musicstreaming.model;

import java.util.Date;

public class Artista extends Autor {

	//Atributos
	private Integer numAlbumes = null;
	private Long numReproducions = null;
	private Date anoFormacion = null;
	private Long numOintes = null;
	private Long numSeguidores = null;
	private Long codEstilo = null;
	
	//Getters & Setters
	public Integer getNumAlbumes() {
		return numAlbumes;
	}
	public void setNumAlbumes(Integer numAlbumes) {
		this.numAlbumes = numAlbumes;
	}
	public Long getNumReproducions() {
		return numReproducions;
	}
	public void setNumReproducions(Long numReproducions) {
		this.numReproducions = numReproducions;
	}
	public Date getAnoFormacion() {
		return anoFormacion;
	}
	public void setAnoFormacion(Date anoFormacion) {
		this.anoFormacion = anoFormacion;
	}
	public Long getNumOintes() {
		return numOintes;
	}
	public void setNumOintes(Long numOintes) {
		this.numOintes = numOintes;
	}
	public Long getNumSeguidores() {
		return numSeguidores;
	}
	public void setNumSeguidores(Long numSeguidores) {
		this.numSeguidores = numSeguidores;
	}
	public Long getCodEstilo() {
		return codEstilo;
	}
	public void setCodEstilo(Long codEstilo) {
		this.codEstilo = codEstilo;
	}
	
	
	
}
