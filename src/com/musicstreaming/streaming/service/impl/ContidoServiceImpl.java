package com.musicstreaming.streaming.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.musicstreaming.streaming.dao.ContidoDAO;
import com.musicstreaming.streaming.dao.impl.ContidoDAOImpl;
import com.musicstreaming.streaming.dao.util.ConnectionManager;
import com.musicstreaming.streaming.dao.util.JDBCUtils;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.model.Contido;
import com.musicstreaming.streaming.service.ContidoCriteria;
import com.musicstreaming.streaming.service.ContidoService;

/**
 * Ejemplo de implementacion mock. 
 *
 * @author https://www.linkedin.com/in/joseantoniolopezperez
 * @version 0.2
 */
public class ContidoServiceImpl implements ContidoService {
	
	private static Logger logger = LogManager.getLogger(CancionServiceImpl.class.getName());
	private ContidoDAO contidoDao = null;

		
	public ContidoServiceImpl() {
		contidoDao = new ContidoDAOImpl();
	}
	
	@Override
	public Contido findById(Long id)
			throws InstanceNotFoundException, DataException {
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			Contido c = contidoDao.findById(connection, id);	

			return c;

		} catch (SQLException e){
			logger.error("idContidoo: "+id, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	
	}
	
	@Override
	public List<? extends Contido> findByCriteria (ContidoCriteria cc, int startIndex, int count)
			throws DataException {
		
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return contidoDao.findByCriteria(connection, startIndex, count, cc);

		} catch (SQLException e){
			logger.error("ContidoCriteria :" + ToStringBuilder.reflectionToString(cc), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}
	
	@Override
	public List<? extends Contido> findTopN (int n, char tipo)
			throws DataException {
		
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return contidoDao.findTopN(n, tipo, connection);

		} catch (SQLException e){
			logger.error("top"+n+"tipo: "+tipo, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
	}
	
	@Override
	public void vota (Long idUsuario, Long idContido, Integer nota) 
			throws DataException{
		
		 Connection connection = null;
	        boolean commit = false;

	        try {

	          
	            connection = ConnectionManager.getConnection();

	            connection.setTransactionIsolation(
	                    Connection.TRANSACTION_READ_COMMITTED);

	            connection.setAutoCommit(false);

	            // Execute action
	            contidoDao.vota(connection, idUsuario, idContido, nota);
	            commit = true;	            

	        } catch (SQLException e) {
	        	logger.error("idUsuario :"+idUsuario+" idContido: "+idContido+" nota: "+ nota, e);
	            throw new DataException(e);
	        } finally {
	        	JDBCUtils.closeConnection(connection, commit);
	        }		
		
	}
	
	@Override
	public Contido create (Contido c)
			throws DataException{
		
		 Connection connection = null;
	        boolean commit = false;

	        try {

	          
	            connection = ConnectionManager.getConnection();

	            connection.setTransactionIsolation(
	                    Connection.TRANSACTION_READ_COMMITTED);

	            connection.setAutoCommit(false);

	            // Execute action
	            Contido result = contidoDao.create(connection, c);
	            commit = true;            
	            return result;
	        } catch (SQLException e) {
	        	logger.error("Contido: "+ToStringBuilder.reflectionToString(c),e);
	            throw new DataException(e);
	       
	        } finally {
	        	JDBCUtils.closeConnection(connection, commit);
	        }		
		
		
	}
		
}