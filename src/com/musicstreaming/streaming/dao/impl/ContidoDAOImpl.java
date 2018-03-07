package com.musicstreaming.streaming.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.musicstreaming.streaming.dao.AlbumDAO;
import com.musicstreaming.streaming.dao.CancionDAO;
import com.musicstreaming.streaming.dao.ContidoDAO;
import com.musicstreaming.streaming.dao.PlaylistDAO;
import com.musicstreaming.streaming.dao.util.JDBCUtils;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.model.Contido;
import com.musicstreaming.streaming.service.ContidoCriteria;

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

	public List<Contido> findByCriteria(Connection connection, int startIndex, int count, ContidoCriteria cc)
			throws InstanceNotFoundException, DataException{

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
					c = findById(connection,resultSet.getLong(1));
					results.add(c);
				} while ((currentCount < count) && resultSet.next()) ;
			}

			return results;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}



	}

	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" WHERE ": " OR ").append(clause);
	}

	protected void loadNext(Connection con, ResultSet rs, Contido c)
			throws SQLException{

		int i = 1;	
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

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		String queryString = "SELECT AVG(NOTA) FROM USUARIO_VOTA_CONTIDO WHERE COD_CONTIDO = "+c.getCodContido();


	}

	public void vota(Connection connection, Long idUsuario, Long idContido, Integer nota) 
			throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String queryString = "SELECT COD_CONTIDO, COD_USUARIO FROM USUARIO_VOTA_CONTIDO WHERE COD_CONTIDO = ? AND COD_USUARIO = ?";
			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {

				queryString = 
						"INSERT INTO USUARIO_VOTA_CONTIDO (COD_USUARIO, COD_CONTIDO, NOTA) VALUES (?, ?, ?) "; 


				preparedStatement = connection.prepareStatement(queryString,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				int i = 1;                
				preparedStatement.setLong(i++, idUsuario);
				preparedStatement.setLong(i++, idContido);
				preparedStatement.setInt(i++, nota);

				int insertedRows = preparedStatement.executeUpdate();
				if (insertedRows == 0) {
					throw new SQLException("Can not add row to table 'USUARIO_VOTA_CONTIDO'");
				}



			} else {

				queryString = 
						"UPDATE USUARIO_VOTA_CONTIDO SET COD_USUARIO = ?, COD_CONTIDO = ?, NOTA = ?";
				preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				int i = 1;                
				preparedStatement.setLong(i++, idUsuario);
				preparedStatement.setLong(i++, idContido);
				preparedStatement.setInt(i++, nota);

				int updatedRows = preparedStatement.executeUpdate();
				if (updatedRows > 1) {
					throw new SQLException("Duplicate row for cod_usuario = '" + 
							idUsuario+",cod_contido = "+idContido+"' in table 'Shippers'");
				}                          


			}
		}catch (SQLException e) {
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  


	}

}