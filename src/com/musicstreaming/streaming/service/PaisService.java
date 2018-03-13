package com.musicstreaming.streaming.service;

import java.util.List;

import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.model.Pais;

public interface PaisService {
	
	public List<Pais> findByIdioma(String idiomaId)
			throws DataException;

}
