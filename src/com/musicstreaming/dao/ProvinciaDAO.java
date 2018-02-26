package com.musicstreaming.dao;

import java.sql.Connection;
import java.util.List;

import com.musicstreaming.model.Provincia;
import com.musicstreaming.streaming.exceptions.DataException;

public interface ProvinciaDAO {
	
	public List<Provincia> findByPaisIdioma (Connection connection, String idPais, String idIdioma)
			throws DataException;

}
