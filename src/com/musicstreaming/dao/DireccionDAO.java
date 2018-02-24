package com.musicstreaming.dao;

import java.sql.Connection;

import com.musicstreaming.model.Direccion;
import com.musicstreaming.model.Usuario;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.DuplicateInstanceException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;

public interface DireccionDAO {
	
	public Direccion findById (Connection connection, Long id)
		throws InstanceNotFoundException, DataException;

	public Direccion create(Connection connection, Usuario u) 
     		throws DuplicateInstanceException, DataException;

}
