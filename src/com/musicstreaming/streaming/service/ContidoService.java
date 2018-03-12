package com.musicstreaming.streaming.service;

import java.util.List;

import com.musicstreaming.streaming.model.Contido;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;

public interface ContidoService {
	
	public Contido findById(Long id)
			throws InstanceNotFoundException, DataException;
		
	public List<Contido> findByCriteria (ContidoCriteria cc)
			throws DataException;
	
	public Contido create (Contido c)
		throws DataException; 

}
