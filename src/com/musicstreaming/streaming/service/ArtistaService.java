package com.musicstreaming.streaming.service;

import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.model.Artista;

public interface ArtistaService {
	
	public Artista findById(Long id)
			throws InstanceNotFoundException, DataException;	

}
