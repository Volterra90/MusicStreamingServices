package com.musicstreaming.dao;

import java.sql.Connection;
import java.util.List;

import com.musicstreaming.model.Usuario;
import com.musicstreaming.services.UsuarioCriteria;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.DuplicateInstanceException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;

public interface UsuarioDAO {
	
	public Usuario findById(Connection connection, Long id) 
			throws InstanceNotFoundException, DataException;
	
	public List<Usuario> findByCriteria (Connection connection, UsuarioCriteria uc)
			throws DataException;
	
	public Usuario create(Connection connection, Usuario u) 
     		throws DuplicateInstanceException, DataException;
         
}
