package com.musicstreaming.streaming.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.musicstreaming.streaming.dao.CancionDAO;
import com.musicstreaming.streaming.dao.impl.CancionDAOImpl;
import com.musicstreaming.streaming.dao.util.ConnectionManager;
import com.musicstreaming.streaming.dao.util.JDBCUtils;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.model.Cancion;
import com.musicstreaming.streaming.service.CancionService;

public class CancionServiceImpl implements CancionService {

	private static Logger logger = LogManager.getLogger(CancionServiceImpl.class.getName());
	private CancionDAO cancionDao = null;

	public CancionServiceImpl () {
		cancionDao = new CancionDAOImpl();
	}

	public List<Cancion> findByGrupo (int startIndex, int count, Long id)
			throws DataException{
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return cancionDao.findByGrupo(connection, startIndex, count, id);

		} catch (SQLException e){
			logger.error("idGrupo :" + id, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}
}

