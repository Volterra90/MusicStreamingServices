package com.musicstreaming.streaming.service;

import java.util.List;

import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.model.Cancion;

public interface CancionService {
	
	public List<Cancion> findByGrupo (int startIndex, int count, Long id)
			throws DataException;

}
