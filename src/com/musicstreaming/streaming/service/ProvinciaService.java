package com.musicstreaming.streaming.service;

import java.util.List;

import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.model.Provincia;

public interface ProvinciaService {
	
	public List<Provincia> findByPaisIdioma (String codPais, String codIdioma)
			throws DataException;
	
	public List<Provincia> findAll(String codIdioma,
		 	int startIndex, int count) 
	throws DataException;

}
