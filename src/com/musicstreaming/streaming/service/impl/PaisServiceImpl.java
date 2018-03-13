package com.musicstreaming.streaming.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.musicstreaming.streaming.dao.PaisDAO;
import com.musicstreaming.streaming.dao.impl.PaisDAOImpl;
import com.musicstreaming.streaming.dao.util.ConnectionManager;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.model.Pais;
import com.musicstreaming.streaming.service.PaisService;

public class PaisServiceImpl implements PaisService {
	
	private PaisDAO paisDao = null;
	
	public PaisServiceImpl() {
		paisDao = new PaisDAOImpl();
	}
	
	public List<Pais> findByIdioma(String idiomaId)
			throws DataException{
		
		Connection connection = null;
		
		try {
			
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);
			
			return paisDao.findByIdioma(connection, idiomaId);
			
		} catch (SQLException e){
			throw new DataException(e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataException(e);
				}
			}
		}
		
	}

}
