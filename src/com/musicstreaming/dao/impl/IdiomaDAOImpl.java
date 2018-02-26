package com.musicstreaming.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.musicstreaming.dao.IdiomaDAO;
import com.musicstreaming.model.Idioma;
import com.musicstreaming.streaming.dao.util.JDBCUtils;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;

public class IdiomaDAOImpl implements IdiomaDAO{

	public IdiomaDAOImpl() {}
	
	@Override
	public Idioma findById(Connection connection, String id) 
			throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT i.COD_IDIOMA, i.IDIOMA"
							+ "FROM IDIOMA i  " +
							"WHERE i.COD_IDIOMA LIKE ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setString(i++, id);

			resultSet = preparedStatement.executeQuery();

			Idioma idi = null;

			if (resultSet.next()) {
				idi = loadNext(connection, resultSet);				
			} else {
				throw new InstanceNotFoundException("Order with id " + id + 
						"not found", Idioma.class.getName());
			}

			return idi;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  
	}
	
	@Override
	public List<Idioma> findAll (Connection connection)
		throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			// Create "preparedStatement"       
			String queryString = 
					"SELECT i.COD_IDIOMA, i.IDIOMA " + 
					"FROM IDIOMA i  " +
					"ORDER BY i.IDIOMA DESC ";
	

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			

			resultSet = preparedStatement.executeQuery();

			// Recupera la pagina de resultados
			List<Idioma> results = new ArrayList<Idioma>();                        
			Idioma i = null;

			while (resultSet.next()) {
				i = loadNext (connection,resultSet);
				results.add(i);
			}
			return results;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	
	private Idioma loadNext(Connection connection, ResultSet resultSet)
			throws SQLException, DataException {

				int i = 1;
				String idIdioma = resultSet.getString(i++);	                
				String idioma = resultSet.getString(i++);	
		
				Idioma idi = new Idioma();		
				idi.setIdIdioma(idIdioma);
				idi.setIdioma(idioma);
				
				return idi;
			}

	
}
