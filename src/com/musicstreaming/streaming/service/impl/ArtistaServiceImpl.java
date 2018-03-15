package com.musicstreaming.streaming.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.musicstreaming.streaming.dao.ArtistaDAO;
import com.musicstreaming.streaming.dao.impl.ArtistaDAOImpl;
import com.musicstreaming.streaming.dao.util.ConnectionManager;
import com.musicstreaming.streaming.dao.util.JDBCUtils;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.model.Artista;
import com.musicstreaming.streaming.service.ArtistaService;

public class ArtistaServiceImpl implements ArtistaService {
	
	private static Logger logger = LogManager.getLogger(ArtistaServiceImpl.class.getName());
	private ArtistaDAO artistaDao = null;
	
	public ArtistaServiceImpl() {
		artistaDao = new ArtistaDAOImpl();
	}
	
	public Artista findById(Long id)
			throws InstanceNotFoundException, DataException{
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			Artista a = artistaDao.findById(connection, id);	

			return a;

		} catch (SQLException e){
			logger.error("idUsuario: "+id, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}
	
	public List<Artista> findByNombre (String nomeArtista, int startIndex, int count)
			throws DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return artistaDao.findByNombre(connection, nomeArtista, startIndex, count);

		} catch (SQLException e){
			logger.error("nomeArtista :" + nomeArtista, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

}
