package com.musicstreaming.streaming.dao;

import java.sql.Connection;

import com.musicstreaming.model.Artista;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;

public interface ArtistaDAO {
	
	public Artista findById(Connection connection, Long id)
		throws InstanceNotFoundException, DataException;
	
	

}
