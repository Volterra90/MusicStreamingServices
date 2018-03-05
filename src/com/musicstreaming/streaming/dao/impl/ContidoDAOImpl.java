package com.musicstreaming.streaming.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.musicstreaming.streaming.dao.CancionDAO;
import com.musicstreaming.streaming.dao.ContidoDAO;
import com.musicstreaming.streaming.model.Contido;
import com.musicstreaming.streaming.service.ContidoCriteria;
import com.sacra.ecommerce.dao.impl.Pedido;
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

			Character tipo = resultSet.getString(0).charAt(0);
			
			Contido c = null;
			
			switch (tipo) {
				case 'C': c = cancionDAO.findById(connection, id);
				case 'A': c = albumDAO.findById(connection, id);
				case 'P': c = playlistDAO.findById(connection, id);
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
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
    
			queryString = new StringBuilder(
					"SELECT c.COD_CONTIDO FROM Contido c ");
			
			// Marca (flag) de primera clausula, que se desactiva en la primera
			boolean first = true;
			
			int i;
			for (i=0; i<cc.getTipos().length; i++){
				if (cc.getTipos()[i]=='A') {
					addClause(queryString, first, " c.TIPO = 'A' ");
					first = false;
				}
				
				else if (cc.getTipos()[i]=='C') {
					addClause(queryString, first, " c.TIPO = 'C' ");
					first = false;
				}
	
				else if (cc.getTipos()[i]=='P') {
					addClause(queryString, first, " c.TIPO = 'P' ");
					first = false;
				}		
			}
			
			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);		
	
			resultSet = preparedStatement.executeQuery();
			
			List<Contido> results = new ArrayList<Contido>();                        
			Contido c = null;
			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					    	
				} while ((currentCount < count) && resultSet.next()) ;
			}

			return results;
	
			} catch (SQLException e) {
				throw new DataException(e);
			} finally {
				JDBCUtils.closeResultSet(resultSet);
				JDBCUtils.closeStatement(preparedStatement);
		}
		
		
		for (cada id) {
			findById(connection,id)
		}
	}
	
	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" WHERE ": " OR ").append(clause);
	}
		
	protected void loadNext(Connection con, ResultSet rs, Contido c) {
		
		int i;	
		Long codContido = rs.getLong(i++);
		String nome = rs.getString(i++);
		Character tipo = rs.getString(i++).charAt(0);
		Long codEstilo = rs.getLong(i++);
		Long codArtista = rs.getLong(i++);
		
		c.setCodArtista(codArtista);
		c.setCodContido(codContido);
		c.setCodEstilo(codEstilo);
		c.setNome(nome);		
		c.setTipo(tipo);
		
	}
	
}