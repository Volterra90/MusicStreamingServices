package com.musicstreaming.streaming.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.musicstreaming.streaming.dao.AlbumDAO;
import com.musicstreaming.streaming.dao.CancionDAO;
import com.musicstreaming.streaming.dao.ContidoDAO;
import com.musicstreaming.streaming.dao.PlaylistDAO;
import com.musicstreaming.streaming.dao.util.JDBCUtils;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.model.Contido;
import com.musicstreaming.streaming.model.Playlist;
import com.musicstreaming.streaming.service.ContidoCriteria;

public class ContidoDAOImpl implements ContidoDAO {

	private static Logger logger = LogManager.getLogger(ContidoDAOImpl.class.getName());

	private CancionDAO cancionDAO = null;
	private AlbumDAO albumDAO = null; 
	private PlaylistDAO playlistDAO = null;



	public ContidoDAOImpl() {
		cancionDAO = new CancionDAOImpl();
		albumDAO = new AlbumDAOImpl();
		playlistDAO = new PlaylistDAOImpl();
	}

	protected ContidoDAOImpl(String s) {		
	}

	@Override 
	public Contido findById(Connection connection, Long id) 
			throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Character tipo = null;
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


			if (resultSet.next()) {	
				tipo = resultSet.getString(1).charAt(0);
			}

			Contido c = null;

			switch (tipo) {
			case 'C': 
				c = cancionDAO.findById(connection, id);
				break;
			case 'A': c = albumDAO.findById(connection, id);
			break;
			case 'P': c = playlistDAO.findById(connection, id);
			break;
			default: throw new IllegalArgumentException();
			}

			return c;

		} catch (SQLException e) {
			logger.fatal("id contido : "+ id, e);
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  

	}

	@Override
	public List<Contido> findByCriteria(Connection connection, int startIndex, int count, ContidoCriteria cc)
			throws InstanceNotFoundException, DataException{

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {

			queryString = new StringBuilder(
					"SELECT c.COD_CONTIDO FROM Contido c ");

			if (!StringUtils.isEmpty(cc.getNomeArtista())) {
				queryString.append("INNER JOIN ARTISTA a ON c.COD_ARTISTA = a.COD_ARTISTA AND a.ARTISTA LIKE ? ");
			}


			boolean first = true;

			int i;
			if (cc.getTipos().length!=3) {
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
			}
			
			if (cc.getNome()!=null) {
				if (!first) {
					queryString.append(" AND UPPER(c.NOME) LIKE ?");
				}else {
					queryString.append(" WHERE UPPER(c.NOME) LIKE ?");
				}
			}
			
			if(cc.getCodArtista()!=null) {
				queryString.append(" AND c.COD_ARTISTA = ?");
			}
			
			
			if (logger.isDebugEnabled()) {
				logger.debug(queryString);
			}

			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);		
			
			i = 1;
			if (!StringUtils.isEmpty(cc.getNomeArtista())) {
				preparedStatement.setString(i++, "%"+cc.getNomeArtista()+"%");
			}
			
			if (cc.getNome()!=null){
				preparedStatement.setString(i++, "%"+cc.getNome()+"%");
			}
			
			if (cc.getCodArtista()!=null) {
				preparedStatement.setLong(i++, cc.getCodArtista());
			}
			
			resultSet = preparedStatement.executeQuery();

			List<Contido> results = new ArrayList<Contido>();                        
			Contido c = null;
			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					c = findById(connection,resultSet.getLong(1));
					results.add(c);
					currentCount++;
				} while ((currentCount < count) && resultSet.next()) ;
			}

			return results;

		} catch (SQLException e) {
			logger.fatal("Criteria-contido : "+ ToStringBuilder.reflectionToString(cc), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}



	}
	
	public List<Contido> findTopN (int n, char tipo, Connection connection)
			throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {

			queryString = new StringBuilder(
					" SELECT c.COD_CONTIDO"
							+ " FROM USUARIO_VOTA_CONTIDO uc"
							+" INNER JOIN CONTIDO c"
							+ " ON c.COD_CONTIDO = uc.COD_CONTIDO AND c.TIPO = '"+tipo
							+ "' GROUP BY uc.COD_CONTIDO"
							+ " ORDER BY AVG(uc.nota) DESC"
							+ " LIMIT ? ");

			if (logger.isDebugEnabled()){
				logger.debug(queryString);
			}

			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);		

			int i = 1;
			preparedStatement.setInt(i++, n);

			resultSet = preparedStatement.executeQuery();

			List<Contido> results = new ArrayList<Contido>();                        
			Contido c = null;

			while (resultSet.next()){
				c = findById(connection,resultSet.getLong(1));
				results.add(c);
			}

			return results;

		} catch (SQLException e) {
			logger.fatal("top"+n+",tipo:"+tipo, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" WHERE ": " OR ").append(clause);
	}

	protected void loadNext(Connection connection, ResultSet rs, Contido c)
			throws SQLException{

		int i = 1;	
		Integer media = 0;
		
		Long codContido = rs.getLong(i++);
		String nome = rs.getString(i++);
		Long codEstilo = rs.getLong(i++);
		Long codArtista = rs.getLong(i++);

		c.setCodArtista(codArtista);
		c.setCodContido(codContido);
		c.setCodEstilo(codEstilo);
		c.setNome(nome);		

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		String queryString = "SELECT AVG(NOTA) FROM USUARIO_VOTA_CONTIDO WHERE COD_CONTIDO = "+c.getCodContido();
		preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			media = resultSet.getInt(1);
		}
		
		c.setMedia(media);

	}

	@Override
	public void vota(Connection connection, Long idUsuario, Long idContido, Integer nota) 
			throws DataException{

		String queryString = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		Boolean exists = null;
		try {
			queryString = "SELECT COD_CONTIDO, COD_USUARIO FROM USUARIO_VOTA_CONTIDO WHERE COD_CONTIDO = ? AND COD_USUARIO = ?";
			preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			int i = 1;
			preparedStatement.setLong(i++, idContido);
			preparedStatement.setLong(i++, idUsuario);

			resultSet = preparedStatement.executeQuery();
			exists = resultSet.next();
		} catch (SQLException e) {
			logger.fatal("idUsuario: "+idUsuario+", idContido: "+idContido + ", nota: "+nota, e);
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		} 

		try {

			if (!exists) {

				queryString = "INSERT INTO USUARIO_VOTA_CONTIDO (COD_USUARIO, COD_CONTIDO, NOTA) VALUES (?, ?, ?) "; 

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
						"UPDATE USUARIO_VOTA_CONTIDO SET NOTA = ? WHERE COD_USUARIO = ? AND COD_CONTIDO = ?";
				preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				
				int i = 1; 
				preparedStatement.setInt(i++, nota);
				preparedStatement.setLong(i++, idUsuario);
				preparedStatement.setLong(i++, idContido);
				

				int updatedRows = preparedStatement.executeUpdate();
				if (updatedRows > 1) {
					throw new SQLException("Duplicate row for cod_usuario = '" + 
							idUsuario+",cod_contido = "+idContido+"' in table 'Shippers'");
				}                          
			}
			
			
			
		} catch (SQLException e) {
			logger.debug(queryString);
			logger.fatal("idUsuario: "+idUsuario+", idContido: "+idContido + ", nota: "+nota, e);
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  


	}

	@Override
	public Contido create(Connection connection, Contido c)
			throws InstanceNotFoundException, DataException{
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"INSERT INTO CONTIDO (NOME, COD_ESTILO, TIPO) VALUES (?, ?, ?) "; 

			preparedStatement = connection.prepareStatement(queryString,
					Statement.RETURN_GENERATED_KEYS);

			int i = 1;                

			preparedStatement.setString(i++, c.getNome());
			preparedStatement.setLong(i++, c.getCodEstilo());
			preparedStatement.setString(i++, String.valueOf(c.getTipo()));
			
			
			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Contido'");
			} 


			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				Long pk = resultSet.getLong(1); 
				c.setCodContido(pk);
			} else {
				throw new DataException("Unable to fetch autogenerated primary key");
			}

			
			
			Character tipo = c.getTipo();
			
			// if (c instanceof Cancion) {
			//				c = TipoContido.CANCION;
			//			} else if (c instanceof ) +
				
				
				

			switch (tipo) {
			case 'P': c = 
					playlistDAO.create(connection, (Playlist) c);
				break;
			default: throw new UnsupportedOperationException();
			}

			return c;

		} catch (SQLException e) {
			logger.fatal("Contido : "+ ToStringBuilder.reflectionToString(c), e);
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  

	}
}