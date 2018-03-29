package com.musicstreaming.streaming.dao;

import java.sql.Connection;
import java.util.List;

import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.model.Contido;
import com.musicstreaming.streaming.service.ContidoCriteria;

public interface ContidoDAO {
	
	public Contido findById(Connection connection, Long id)
		throws InstanceNotFoundException, DataException;
	
	public List<Contido> findByCriteria (Connection connection, int startIndex, int count, ContidoCriteria cc)
		throws DataException; 
	
	public List<Contido> findTopN (int n, char tipo, Connection connection)
			throws DataException;
	
	public void vota(Connection connection, Long idUsuario, Long idContido, Integer nota) 
		throws DataException;
	
	public Contido create(Connection connection, Contido c)
			throws InstanceNotFoundException, DataException;
	
}
