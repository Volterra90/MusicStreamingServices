package com.musicstreaming.streaming.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.musicstreaming.streaming.dao.PaisDAO;
import com.musicstreaming.streaming.model.Pais;
import com.musicstreaming.streaming.dao.util.JDBCUtils;
import com.musicstreaming.streaming.exceptions.DataException;

public class PaisDAOImpl implements PaisDAO {
	
	public PaisDAOImpl() {}
	
	@Override
	public List<Pais> findByIdioma(Connection connection, String idiomaId)
					throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			// Create "preparedStatement"       
			String queryString = 
					"SELECT p.COD_PAIS, p.PAIS, p.COD_IDIOMA " + 
					"FROM PAIS_IDIOMA p  " +
					"WHERE COD_IDIOMA = ? "+
					"ORDER BY p.PAIS ASC ";
	

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			int i = 1;                
			preparedStatement.setString(i++, idiomaId);

			resultSet = preparedStatement.executeQuery();

			// Recupera la pagina de resultados
			List<Pais> results = new ArrayList<Pais>();                        
			Pais p = null;

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
	
	private Pais loadNext(Connection connection, ResultSet resultSet)
			throws SQLException, DataException {

				int i = 1;
				String idPais = resultSet.getString(i++);	                
				String pais = resultSet.getString(i++);	
				String idIdioma = resultSet.getString(i++);
		
				Pais p = new Pais();		
				p.setCodPais(idPais);;
				p.setPais(pais);
				p.setCodPais(idIdioma);
				
				return p;
			}

		
}