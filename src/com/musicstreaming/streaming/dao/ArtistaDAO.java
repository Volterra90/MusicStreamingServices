package com.musicstreaming.streaming.dao;

import java.sql.Connection;
import java.util.Date;

import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.model.Artista;

public interface ArtistaDAO {
	
	public Artista findById(Connection connection, Long id)
		throws InstanceNotFoundException, DataException;
	
}
