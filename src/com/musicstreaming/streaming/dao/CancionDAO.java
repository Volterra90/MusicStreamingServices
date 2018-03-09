package com.musicstreaming.streaming.dao;

import java.sql.Connection;
import java.util.List;

import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.model.Cancion;

public interface CancionDAO {

	public Cancion findById(Connection connection,Long id) 
			throws InstanceNotFoundException, DataException;
	
	public List<Cancion> findByGrupoCancions (Connection connection, Long id)
			throws DataException;
	
}
