package com.musicstreaming.streaming.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.musicstreaming.streaming.dao.ProvinciaDAO;
import com.musicstreaming.streaming.dao.util.JDBCUtils;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.model.Provincia;

public class ProvinciaDAOImpl implements ProvinciaDAO {
	
public ProvinciaDAOImpl() {}
private static Logger logger = LogManager.getLogger(ProvinciaDAOImpl.class.getName());
	
	@Override
	public List<Provincia> findByPaisIdioma(Connection connection, String codPais, String codIdioma)
					throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			// Create "preparedStatement"       
			String queryString = 
					"SELECT pi.COD_PROVINCIA, pi.PROVINCIA " + 
					"FROM PROVINCIA_IDIOMA pi  " +
					"INNER JOIN PROVINCIA p "+
						"ON p.COD_PROVINCIA = pi.COD_PROVINCIA " +
					" WHERE p.COD_PAIS = ? AND pi.COD_IDIOMA = ?" +
						"ORDER BY pi.PROVINCIA ASC";
	

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			int i = 1;                
			preparedStatement.setString(i++, codPais);
			preparedStatement.setString(i++, codIdioma);

			resultSet = preparedStatement.executeQuery();

			// Recupera la pagina de resultados
			List<Provincia> results = new ArrayList<Provincia>();                        
			Provincia p = null;

			while (resultSet.next()) {
				p = loadNext (resultSet);
				results.add(p);
			}
			return results;

		} catch (SQLException e) {
			logger.fatal("codPais: "+codPais+", codIdioma: "+codIdioma);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	
	public List<Provincia> findAll(Connection connection, String codIdioma,
		 	int startIndex, int count) 
	throws DataException{
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			// Create "preparedStatement"       
			String queryString = 
					"SELECT pi.COD_PROVINCIA, pi.COD_IDIOMA, pi.PROVINCIA " + 
					"FROM PROVINCIA_IDIOMA pi  " +
					"ORDER BY pi.PROVINCIA asc ";
			// Debatir sobre ordenacion por ... y opciones

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			// Execute query.
			resultSet = preparedStatement.executeQuery();

			// Recupera la pagina de resultados
			List<Provincia> results = new ArrayList<Provincia>();                        
			Provincia p = null;
			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					p = loadNext(resultSet);
					results.add(p);               	
					currentCount++;                	
				} while ((currentCount < count) && resultSet.next()) ;
			}

			return results;

		} catch (SQLException e) {
			logger.error("codIdioma: "+codIdioma+"startIndex: "+startIndex + ", count: "+count, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	
	private Provincia loadNext(ResultSet resultSet)
			throws SQLException, DataException {

				int i = 1;
				Long idProvincia = resultSet.getLong(i++);	                
				String provincia = resultSet.getString(i++);
		
		
				Provincia p = new Provincia();		
				p.setCodProvincia(idProvincia);
				p.setProvincia(provincia);
				
				return p;
			}


}
