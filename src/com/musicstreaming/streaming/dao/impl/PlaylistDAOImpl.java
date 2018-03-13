package com.musicstreaming.streaming.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.musicstreaming.streaming.dao.PlaylistDAO;
import com.musicstreaming.streaming.dao.util.JDBCUtils;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.DuplicateInstanceException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.model.Direccion;
import com.musicstreaming.streaming.model.Playlist;

public class PlaylistDAOImpl extends ContidoDAOImpl implements PlaylistDAO {
	
	public PlaylistDAOImpl() {}
	private static Logger logger = LogManager.getLogger(PlaylistDAOImpl.class.getName());
	
	@Override
	public Playlist findById(Connection connection,Long id) 
			throws InstanceNotFoundException, DataException{
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT c.COD_CONTIDO, c.NOME, c.TIPO, c.COD_AUTOR, c.COD_ESTILO, c.COD_ARTISTA, a.COD_USUARIO " 
							+ "FROM CONTIDO c"
							+ "INNER JOIN GRUPO_CANCIONS gc "
							+ "ON c.COD_CONTIDO = gc.COD_GRUPO "
							+ "INNER JOIN PLAYLIST p "
							+ "ON gc.COD_GRUPO = p.COD_PLAYLIST "
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
			logger.fatal("idContido "+id);;
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
	
	@Override
	public Playlist create(Connection connection, Playlist p) 
     		throws DuplicateInstanceException, DataException{
		
		PreparedStatement preparedStatement = null;
		try {          
			
			String queryString = "INSERT INTO PLAYLIST(COD_PLAYLIST, COD_USUARIO)"
					+ "VALUES (?, ?)";

			preparedStatement = connection.prepareStatement(queryString,
					Statement.RETURN_GENERATED_KEYS);
			
			int i = 1;     
			preparedStatement.setLong(i++, p.getCodContido());
			preparedStatement.setLong(i++, p.getCodUsuario());

			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Playlist'");
			} 
			

			return p;

		} catch (SQLException e) {
			logger.fatal("Playlist: "+ToStringBuilder.reflectionToString(p));
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
		
		
	}
	
	
}
