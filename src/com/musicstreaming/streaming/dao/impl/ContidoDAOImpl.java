package com.musicstreaming.streaming.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.musicstreaming.streaming.dao.CancionDAO;
import com.musicstreaming.streaming.dao.ContidoDAO;
import com.musicstreaming.model.Contido;
import com.musicstreaming.services.ContidoCriteria;
import com.musicstreaming.streaming.dao.util.JDBCUtils;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;

public class ContidoDAOImpl implements ContidoDAO {
	
	private CancionDAO cancionDAO = null;
	private AlbumDAO albumDAO = null; 
	private PlaylistDAO playlistDAO = null;
	
	public ContidoDAOImpl() {
		cancionDAO = new CancionDAOImpl();
		albumDAO = new AlbumDAOImpl();
		playlistDAO = new PlaylistDAOImpl();
	}
	
			
	public Contido findById(Connection connection, Long id) 
			throws InstanceNotFoundException, DataException {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT c.TIPO " 
					+ "FROM Contido c  " +
					"WHERE c.COD_CONTIDO = ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, id);

			resultSet = preparedStatement.executeQuery();

			String tipo = resultSet.getString(0);
			
			Contido c = null;
			
			switch (tipo) {
				case 'c': c = cancionDAO.findById(connection, id);
				case "a": c = albumDAO.findById(connection, id);
				case "p": c = playlistDAO.findById(connection, id);
			}
			
			

			if (resultSet.next()) {
				c = loadNext(connection, resultSet);				
			} else {
				throw new InstanceNotFoundException("Contido with id " + id + 
						"not found", Contido.class.getName());
			}

			return c;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  
					
	}
	
	public List<Contido> findByCriteria(Connection connection, int startIndex, int pageSize, ContidoCriteria cc) {
		
		
		
		select id // solo recupero 
		from contenido // cancion album playlist
		where todos los necesios, incluyendo 
		
		
		for (cada id) {
			findById(connection,id)
		}
	}
		
	
	protected void loadNext(Conneciton c, Resultset rs, Contenido c) throws ... {
		// Rellena los atributos comunes del resultSet
		c.setXXX(rs.getXXx)
		c.setXXX(rs.getXXx)
		c.setXXX(rs.getXXx)					
		
	}
	
}
;: