package com.musicstreaming.streaming.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.musicstreaming.streaming.dao.CancionDAO;
import com.musicstreaming.streaming.dao.util.JDBCUtils;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.model.Cancion;

public class CancionDAOImpl extends ContidoDAOImpl implements CancionDAO {
	
	private static Logger logger = LogManager.getLogger(CancionDAOImpl.class.getName());
	public CancionDAOImpl() {}

	public Cancion findById(Connection connection, Long id) 
			throws InstanceNotFoundException, DataException{

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {          
			String queryString = 
					"SELECT c.COD_CONTIDO, c.NOME, c.TIPO, c.COD_AUTOR, c.COD_ESTILO, c.COD_ARTISTA, ca.DURACION " 
							+ "FROM CONTIDO c"
							+ "INNER JOIN cancion ca "
							+ "ON c.COD_CANCION = c.COD_CONTIDO "
							+ "WHERE c.COD_CONTIDO = ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, id);

			resultSet = preparedStatement.executeQuery();

			Cancion c = null;

			if (resultSet.next()) {
				c = loadNext(connection, resultSet);				
			} else {
				throw new InstanceNotFoundException("Canción with id " + id + 
						"not found", Cancion.class.getName());
			}

			return c;

		} catch (SQLException e) {
			logger.error("idContido : "+id, e);
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	
	public List<Cancion> findByGrupo(Connection connection, Long id)
			throws DataException{
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String queryString = "SELECT COD_CANCION FROM GRUPO_CONTEN_CANCION WHERE COD_GRUPO = ? ";
			
		
	}

	
	protected Cancion loadNext(Connection connection, ResultSet rs) 
			throws SQLException, DataException{
			 
		// Creo el tipo especifico
		Cancion c = new Cancion();
		
		// El DAO padre carga los atirbutos comunes
		super.loadNext(connection, rs, c);
		
		// Y carga los suyos propios
		Long duracion = rs.getLong(6);	
		c.setDuracion(duracion);
				
		return c;
	}

}
