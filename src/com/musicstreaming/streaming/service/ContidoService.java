package com.musicstreaming.streaming.service;

import java.util.List;

import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.model.Contido;

public interface ContidoService {
		
	public List<Contido> findByCriteria (ContidoCriteria cc)
			throws DataException;
	
	public void vota (Long idUsuario, Long idContido, Integer nota) 
			throws DataException;
	
	public Contido create (Contido c)
		throws DataException; 

}
