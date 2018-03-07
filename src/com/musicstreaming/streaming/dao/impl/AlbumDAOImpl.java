package com.musicstreaming.streaming.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.musicstreaming.streaming.dao.AlbumDAO;
import com.musicstreaming.streaming.dao.util.JDBCUtils;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.model.Album;
import com.musicstreaming.streaming.model.Direccion;

public class AlbumDAOImpl extends ContidoDAOImpl implements AlbumDAO {
	
	public AlbumDAOImpl() {}
	
	public Album findById(Connection connection, Long id) 
			throws InstanceNotFoundException, DataException{

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT c.COD_CONTIDO, c.NOME, c.TIPO, c.COD_AUTOR, c.COD_ESTILO, c.COD_ARTISTA, a.FECHA_PUBLICACION, a.NOME_DISCOGRAFICA " 
							+ "FROM CONTIDO c"
							+ "INNER JOIN ALBUM a "
							+ "ON c.COD_CONTIDO = a.COD_ALBUM "
							+ "WHERE c.COD_CONTIDO = ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, id);

			resultSet = preparedStatement.executeQuery();

			Album a = null;

			if (resultSet.next()) {
				a = loadNext(connection, resultSet);				
			} else {
				throw new InstanceNotFoundException("Album with id " + id + 
						"not found", Direccion.class.getName());
			}

			return a;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	
	protected Album loadNext(Connection connection, ResultSet rs) 
			throws SQLException, DataException{
			 
		// Creo el tipo especifico
		Album a = new Album();
		
		// El DAO padre carga los atirbutos comunes
		super.loadNext(connection, rs, a);
		
		// Y carga los suyos propios
		Date fecha = rs.getDate(6);	
		String discografica = rs.getString(7);
		
		a.setFechaPublicacion(fecha);
		a.setNomeDiscografica(discografica);
				
		return a;
	}

	

}
