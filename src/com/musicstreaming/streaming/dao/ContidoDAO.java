package com.musicstreaming.streaming.dao;

import java.sql.Connection;
import java.util.List;

import com.musicstreaming.streaming.model.Contido;
import com.musicstreaming.streaming.service.ContidoCriteria;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;

public interface ContidoDAO {
	
	public Contido findById(Connection c, Long id)
		throws InstanceNotFoundException, DataException;
	
	public List<Contido> findByCriteria (Connection connection, int startIndex, int count, ContidoCriteria cc)
		throws DataException;
	

}
