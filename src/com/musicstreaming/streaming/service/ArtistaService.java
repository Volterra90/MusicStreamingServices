package com.musicstreaming.streaming.service;

import java.util.List;

import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.model.Artista;

public interface ArtistaService {
	
	public Artista findById(Long id)
			throws InstanceNotFoundException, DataException;
		
		public List<Artista> findByNombre (String nomeArtista, int startIndex, int count)
			throws DataException;

}
