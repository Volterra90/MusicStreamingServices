package com.musicstreaming.streaming.model;

import java.util.List;

public abstract class GrupoCancions extends Contido {
	
	private List<Cancion> cancions = null;
	
	public GrupoCancions() {}

	public List<Cancion> getCancions() {
		return cancions;
	}

	public void setCancions(List<Cancion> cancions) {
		this.cancions = cancions;
	}
	
	
}