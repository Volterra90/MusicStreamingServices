package com.musicstreaming.dao;

import java.sql.Connection;
import java.util.List;

import com.musicstreaming.model.Contido;
import com.musicstreaming.services.ContidoCriteria;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;

public interface ContidoDAO {
	
	public Contido findById(Connection c, Long id)
		throws InstanceNotFoundException, DataException;
	
	public List<Contido> findByCriteria (Connection connection, ContidoCriteria cc)
		throws DataException;
	

}