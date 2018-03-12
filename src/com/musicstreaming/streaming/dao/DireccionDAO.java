package com.musicstreaming.streaming.dao;

import java.sql.Connection;
import java.util.List;

import com.musicstreaming.streaming.model.Direccion;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.DuplicateInstanceException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;

public interface DireccionDAO {
	
	public Direccion findById(Connection connection, Long id)
		throws InstanceNotFoundException, DataException;
	
	public List<Direccion> findByIdUsuario (Connection connection, Long id)
		throws DataException;
	
	public Direccion create(Connection connection, Direccion d) 
 		throws DuplicateInstanceException, DataException;
	
	public void update (Connection connection, Direccion d)
		throws InstanceNotFoundException, DataException;
	
	public long delete(Connection connection, Long id)
		throws DataException;
     
}
