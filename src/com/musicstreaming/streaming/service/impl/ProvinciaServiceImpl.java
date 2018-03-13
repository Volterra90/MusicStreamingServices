package com.musicstreaming.streaming.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.musicstreaming.streaming.dao.ProvinciaDAO;
import com.musicstreaming.streaming.dao.impl.ProvinciaDAOImpl;
import com.musicstreaming.streaming.dao.util.ConnectionManager;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.model.Provincia;
import com.musicstreaming.streaming.service.ProvinciaService;

public class ProvinciaServiceImpl implements ProvinciaService{

	private ProvinciaDAO provinciaDao = null;

	public ProvinciaServiceImpl() {
		provinciaDao = new ProvinciaDAOImpl();
	}

	public List<Provincia> findByPaisIdioma (String codPais, String codIdioma)
			throws DataException{
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return provinciaDao.findByPaisIdioma(connection, codPais, codIdioma);

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

