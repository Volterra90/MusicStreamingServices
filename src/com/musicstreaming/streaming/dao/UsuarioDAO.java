package com.musicstreaming.streaming.dao;

import java.sql.Connection;

import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.DuplicateInstanceException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.model.Usuario;

public interface UsuarioDAO {
	
	public Usuario findUserById(Connection connection, String id) 
			throws InstanceNotFoundException, DataException;
	
	public Boolean exists(Connection connection, String nick, String email) 
			throws DataException;
	
	public Usuario create(Connection connection, Usuario u) 
     		throws DuplicateInstanceException, DataException;
	
}
