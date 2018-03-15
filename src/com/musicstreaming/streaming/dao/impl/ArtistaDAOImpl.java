package com.musicstreaming.streaming.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.musicstreaming.streaming.dao.ArtistaDAO;
import com.musicstreaming.streaming.dao.util.JDBCUtils;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.model.Artista;
import com.musicstreaming.streaming.model.Cancion;

public class ArtistaDAOImpl implements ArtistaDAO {

	private static Logger logger = LogManager.getLogger(ArtistaDAOImpl.class.getName());
	public ArtistaDAOImpl() {}

	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	@Override
	public Artista findById(Connection connection, Long id)
			throws InstanceNotFoundException, DataException{

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

			Artista a = null;

			if (resultSet.next()) {
				a = loadNext(resultSet);				
			} else {
				throw new InstanceNotFoundException("Canción with id " + id + 
						"not found", Cancion.class.getName());
			}

			return a;

		} catch (SQLException e) {
			logger.error("idContido : "+id, e);
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}

	}

	@Override
	public List<Artista> findByNombre (Connection connection, String nomeArtista, int startIndex, int count)
			throws DataException{

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			// Create "preparedStatement"       
			String queryString = 
					"SELECT a.COD_ARTISTA, a.ANO_FORMACION, a.NOME_ARTISTA " + 
							"FROM ARTISTA a  " +
							"WHERE UPPER(s.NOME_ARTISTA) LIKE ? "+
							"ORDER BY a.NOME_ARTISTA asc ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			// Fill parameters
			int i = 1;                
			preparedStatement.setString(i++, "%" + nomeArtista.toUpperCase() + "%");
			// Nos prevenimos de SQL Injection:
			// http://en.wikipedia.org/wiki/SQL_injection
			// http://unixwiz.net/techtips/sql-injection.html


			// Execute query.
			resultSet = preparedStatement.executeQuery();

			// Recupera la pagina de resultados
			List<Artista> results = new ArrayList<Artista>();                        
			Artista a = null;
			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					a = loadNext(resultSet);
					results.add(a);               	
					currentCount++;                	
				} while ((currentCount < count) && resultSet.next()) ;
			}

			return results;

		} catch (SQLException e) {
			logger.error("name: "+nomeArtista + ", startIndex: "+startIndex + ", count: "+count, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}

	}
	
	private Artista loadNext(ResultSet resultSet)
			throws SQLException {

		// Recupera los atributos asumiendo un orden comun
		int i = 1;
		Long codArtista = resultSet.getLong(i++);	                
		Date anoFormacion = resultSet.getDate(i++);	                
		String nomeArtista = resultSet.getString(i++);

		// Rellena el objeto
		Artista a = new Artista();
		a.setAnoFormacion(anoFormacion);
		a.setCodArtista(codArtista);
		a.setNomeArtista(nomeArtista);
		return a;
	}
}

