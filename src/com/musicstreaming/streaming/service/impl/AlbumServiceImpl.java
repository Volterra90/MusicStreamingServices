package com.musicstreaming.streaming.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.musicstreaming.streaming.dao.AlbumDAO;
import com.musicstreaming.streaming.dao.impl.AlbumDAOImpl;
import com.musicstreaming.streaming.dao.util.ConnectionManager;
import com.musicstreaming.streaming.dao.util.JDBCUtils;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.model.Album;
import com.musicstreaming.streaming.service.AlbumService;

public class AlbumServiceImpl implements AlbumService{
	
	private static Logger logger = LogManager.getLogger(AlbumServiceImpl.class.getName());
	private AlbumDAO albumDao = null;
	
	public AlbumServiceImpl() {
		albumDao = new AlbumDAOImpl();
	}
	
	public Album findById(Long id) 
			throws InstanceNotFoundException, DataException{
		
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			Album a = albumDao.findById(connection, id);	

			return a;

		} catch (SQLException e){
			logger.error("idUsuario: "+id, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}

}
