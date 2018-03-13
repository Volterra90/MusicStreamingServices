package com.musicstreaming.streaming.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.musicstreaming.streaming.dao.UsuarioDAO;
import com.musicstreaming.streaming.dao.impl.UsuarioDAOImpl;
import com.musicstreaming.streaming.dao.util.ConnectionManager;
import com.musicstreaming.streaming.dao.util.JDBCUtils;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.exceptions.MailException;
import com.musicstreaming.streaming.model.Usuario;
import com.musicstreaming.streaming.service.MailService;
import com.musicstreaming.streaming.service.UsuarioService;

public class UsuarioServiceImpl implements UsuarioService{

	private static Logger logger = LogManager.getLogger(UsuarioServiceImpl.class.getName());
	private UsuarioDAO usuarioDao = null;
	private MailService mailService = null;

	public UsuarioServiceImpl() {
		usuarioDao = new UsuarioDAOImpl();
		mailService = new MailServiceImpl();
	}

	public Usuario findById (Long id)
			throws InstanceNotFoundException, DataException{

		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			Usuario u = usuarioDao.findById(connection, id);	

			return u;

		} catch (SQLException e){
			logger.error("idUsuario: "+id, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}


	}


	public Usuario create(Usuario u) 
			throws DataException, MailException {

		Connection connection = null;
		boolean commit = false;

		try {

			connection = ConnectionManager.getConnection();

			connection.setTransactionIsolation(
					Connection.TRANSACTION_READ_COMMITTED);

			connection.setAutoCommit(false);

			// Execute action
			Usuario result = usuarioDao.create(connection, u);

			mailService.sendMail("Benvido a MusicStreaming", "Benvido a MusicStreaming!", u.getEmail());

			commit = true;

			return result;

		} catch (SQLException e) {
			logger.fatal("Usuario: " + ToStringBuilder.reflectionToString(u));
			throw new DataException(e);   
		} 
		finally {
			JDBCUtils.closeConnection(connection, commit);
		}		
	}


}
