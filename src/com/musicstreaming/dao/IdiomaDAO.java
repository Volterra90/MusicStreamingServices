package com.musicstreaming.dao;

import java.sql.Connection;
import java.util.List;

import com.musicstreaming.model.Idioma;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;

public interface IdiomaDAO {
	
	public Idioma findById (Connection connection, String id)
			throws InstanceNotFoundException, DataException;

	public List<Idioma> findAll (Connection connection)
			throws DataException;

}
