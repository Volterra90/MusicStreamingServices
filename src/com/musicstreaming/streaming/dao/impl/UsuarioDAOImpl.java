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
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.model.Direccion;
import com.musicstreaming.streaming.model.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {
	
	public UsuarioDAOImpl() {}
	private static Logger logger = LogManager.getLogger(UsuarioDAOImpl.class.getName());
	
	@Override
	public Usuario findById (Connection connection, Long id)
		throws InstanceNotFoundException, DataException{
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT u.COD_USUARIO, u.FECHA_SUBSCRICION, u.XENERO, u.FECHA_NACEMENTO, u.E_MAIL, u.CONTRASINAL, u.NOME, u.APELIDOS, u.NICK " 
							+ "FROM Usuario u  " +
							"WHERE u.COD_USUARIO = ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, id);

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
     		throws DataException{
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {          
			
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
				u.getDireccion().setCodUsuario(pk);
			} else {
				throw new DataException("Unable to fetch autogenerated primary key");
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
