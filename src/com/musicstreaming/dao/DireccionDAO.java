package com.musicstreaming.dao;

import java.sql.Connection;

import com.musicstreaming.model.Direccion;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.DuplicateInstanceException;

public interface DireccionDAO {
	
	public Direccion create(Connection connection, Direccion d) 
     		throws DuplicateInstanceException, DataException;
     
}
