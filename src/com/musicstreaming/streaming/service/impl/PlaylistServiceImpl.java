package com.musicstreaming.streaming.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.musicstreaming.streaming.dao.PlaylistDAO;
import com.musicstreaming.streaming.dao.impl.PlaylistDAOImpl;
import com.musicstreaming.streaming.dao.util.ConnectionManager;
import com.musicstreaming.streaming.dao.util.JDBCUtils;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.model.Playlist;
import com.musicstreaming.streaming.service.PlaylistService;

public class PlaylistServiceImpl implements PlaylistService {
	private static Logger logger = LogManager.getLogger(CancionServiceImpl.class.getName());
	private PlaylistDAO playlistDao = null;
	
	public PlaylistServiceImpl () {
		playlistDao = new PlaylistDAOImpl();
	}
	
	public List<Playlist> findByUsuario (Long idUsuario)
			throws DataException{
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return playlistDao.findByUsuario(connection, idUsuario);

		} catch (SQLException e){
			logger.error("idUsuario :" + idUsuario, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}
}
