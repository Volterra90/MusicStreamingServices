package com.musicstreaming.streaming.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.musicstreaming.streaming.dao.PlaylistDAO;
import com.musicstreaming.streaming.dao.util.JDBCUtils;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.model.Direccion;
import com.musicstreaming.streaming.model.Playlist;

public class PlaylistDAOImpl extends ContidoDAOImpl implements PlaylistDAO {
	
	public PlaylistDAOImpl() {}
	
	public Playlist findById(Connection connection,Long id) 
			throws InstanceNotFoundException, DataException{
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT c.COD_CONTIDO, c.NOME, c.TIPO, c.COD_AUTOR, c.COD_ESTILO, c.COD_ARTISTA, a.COD_USUARIO " 
							+ "FROM CONTIDO c"
							+ "INNER JOIN PLAYLIST p "
							+ "ON c.COD_CONTIDO = p.COD_PLAYLIST "
							+ "WHERE c.COD_CONTIDO = ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, id);

			resultSet = preparedStatement.executeQuery();

			Playlist p = null;

			if (resultSet.next()) {
				p = loadNext(connection, resultSet);				
			} else {
				throw new InstanceNotFoundException("Album with id " + id + 
						"not found", Direccion.class.getName());
			}

			return p;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
		
	}
	
	protected Playlist loadNext(Connection connection, ResultSet rs) 
			throws SQLException, DataException{
			 
		// Creo el tipo especifico
		Playlist p = new Playlist();
		
		// El DAO padre carga los atirbutos comunes
		super.loadNext(connection, rs, p);
		
		// Y carga los suyos propios
		Long codUsuario = rs.getLong(6);
		
		p.setCodUsuario(codUsuario);
				
		return p;
	}
	
}
