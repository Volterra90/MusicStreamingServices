package com.musicstreaming.streaming.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.musicstreaming.streaming.dao.UsuarioDAO;
import com.musicstreaming.streaming.dao.util.JDBCUtils;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.DuplicateInstanceException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.model.Direccion;
import com.musicstreaming.streaming.model.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {
	
	public UsuarioDAOImpl() {}
	private static Logger logger = LogManager.getLogger(UsuarioDAOImpl.class.getName());
	
	@Override
	public Usuario findUserById (Connection connection, String id)
		throws InstanceNotFoundException, DataException{
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT u.COD_USUARIO, u.FECHA_SUBSCRICION, u.XENERO, u.FECHA_NACEMENTO, u.E_MAIL, u.CONTRASINAL, u.NOME, u.APELIDOS, u.NICK " 
							+ "FROM Usuario u  " +
							"WHERE u.NICK = ? OR u.E_MAIL = ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setString(i++, id);
			preparedStatement.setString(i++, id);

			resultSet = preparedStatement.executeQuery();

			Usuario u = null;

			if (resultSet.next()) {
				u = loadNext(connection, resultSet);				
			} else {
				throw new InstanceNotFoundException("Usuario with id " + id + 
						"not found", Direccion.class.getName());
			}

			return u;

		} catch (SQLException e) {
			logger.fatal("idUsuario ="+id);
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  
		
	}
	
	@Override
	public Boolean exists(Connection connection, String nick, String email) 
			throws DataException {
		boolean exist = false;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

		// Create preparedStatement
			String queryString = "SELECT COD_USUARIO"
					+ " FROM USUARIO "
					+ " WHERE NICK = ? OR E_MAIL=? ";
			preparedStatement = connection.prepareStatement(queryString);

			
			int i = 1;
			preparedStatement.setString(i++, nick);
			preparedStatement.setString(i++, email);
			
			
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				exist = true;
			}

		} catch (SQLException e) {
			logger.error("Nick: "+nick+", email:"+email, e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}

		return exist;
	}
	
	
	private Usuario loadNext(Connection connection, ResultSet resultSet)
			throws SQLException, DataException {

				int i = 1;
				Long usuarioId = resultSet.getLong(i++);	                
				Date fechaSubscricion = resultSet.getDate(i++);	                
				Character xenero = resultSet.getString(i++).charAt(0);
				Date fechaNacemento = resultSet.getDate(i++);
				String email = resultSet.getString(i++);
				String contrasinal = resultSet.getString(i++);
				String nome = resultSet.getString(i++);
				String apelidos = resultSet.getString(i++);
				String nick = resultSet.getString(i++);
				
		
				Usuario u = new Usuario();		
				u.setApelidos(apelidos);
				u.setContrasinal(contrasinal);
				u.setEmail(email);
				u.setFechaNacemento(fechaNacemento);
				u.setFechaSubscricion(fechaSubscricion);
				u.setNome(nome);
				u.setXenero(xenero);
				u.setIdUsuario(usuarioId);
				u.setNick(nick);
				
				return u;
			}
	
	@Override
	public Usuario create(Connection connection, Usuario u) 
     		throws DuplicateInstanceException, DataException{
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {    
			
			if (exists(connection, u.getNick(), u.getEmail())) {
		        throw new DuplicateInstanceException(u, Usuario.class.getName());
			}else {
			
			String queryString = "INSERT INTO USUARIO(FECHA_SUBSCRICION, XENERO, FECHA_NACEMENTO, E_MAIL, CONTRASINAL, NOME, APELIDOS, NICK)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			preparedStatement = connection.prepareStatement(queryString,
					Statement.RETURN_GENERATED_KEYS);
			
			int i = 1;     
			preparedStatement.setDate(i++,new java.sql.Date(u.getFechaSubscricion().getTime()));
			preparedStatement.setString(i++, u.getXenero().toString());
			preparedStatement.setDate(i++, new java.sql.Date(u.getFechaNacemento().getTime()));
			preparedStatement.setString(i++, u.getEmail());
			preparedStatement.setString(i++, u.getContrasinal());
			preparedStatement.setString(i++, u.getNome());
			preparedStatement.setString(i++, u.getApelidos());
			preparedStatement.setString(i++, u.getNick());

			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Usuario'");
			} 
			
			
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				Long pk = resultSet.getLong(1); 
				u.setIdUsuario(pk);
			} else {
				throw new DataException("Unable to fetch autogenerated primary key");
			}
			}
			
			
			return u;

		} catch (SQLException e) {
			logger.fatal("Usuario: "+ToStringBuilder.reflectionToString(u));
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
		
		
	}
	

}
