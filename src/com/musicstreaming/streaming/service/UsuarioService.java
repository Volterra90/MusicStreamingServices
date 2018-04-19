package com.musicstreaming.streaming.service;

import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.DuplicateInstanceException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.exceptions.MailException;
import com.musicstreaming.streaming.model.Usuario;

public interface UsuarioService {
	
	public Usuario findUserById (String id)
		throws InstanceNotFoundException, DataException;
	
	public Usuario create(Usuario u) 
     		throws DataException, MailException, DuplicateInstanceException;

}
