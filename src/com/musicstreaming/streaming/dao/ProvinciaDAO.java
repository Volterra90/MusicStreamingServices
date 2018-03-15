package com.musicstreaming.streaming.dao;

import java.sql.Connection;
import java.util.List;

import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.model.Provincia;

public interface ProvinciaDAO {
	
	public List<Provincia> findByPaisIdioma (Connection connection, String codPais, String codIdioma)
			throws DataException;
	
	public List<Provincia> findAll(Connection connection, String codIdioma,
		 	int startIndex, int count) 
	throws DataException;

}

