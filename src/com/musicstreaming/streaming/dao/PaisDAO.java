package com.musicstreaming.streaming.dao;

import java.sql.Connection;
import java.util.List;

import com.musicstreaming.model.Pais;
import com.musicstreaming.streaming.exceptions.DataException;

public interface PaisDAO {
	
	public List<Pais> findByIdioma(Connection connection, String idiomaId)
	throws DataException;

}
