package com.musicstreaming.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.musicstreaming.dao.ProvinciaDAO;
import com.musicstreaming.model.Provincia;
import com.musicstreaming.streaming.dao.util.JDBCUtils;
import com.musicstreaming.streaming.exceptions.DataException;

public class ProvinciaDAOImpl implements ProvinciaDAO {
	
public ProvinciaDAOImpl() {}
	
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
				p = loadNext (connection,resultSet);
				results.add(p);
			}
			return results;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	
	private Provincia loadNext(Connection connection, ResultSet resultSet)
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
