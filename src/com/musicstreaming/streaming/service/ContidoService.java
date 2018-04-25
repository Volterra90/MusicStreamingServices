package com.musicstreaming.streaming.service;

import java.util.List;

import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.model.Contido;

/**
 * Interfaz do servicio de negocio ContidoService
 * @author Alberto Taboada Varela
 *
 */
public interface ContidoService {
	
	public Contido findById(Long id)
			throws InstanceNotFoundException, DataException;
	//Devolve un contido segundo a s�a PK.
	
	public List<? extends Contido> findByCriteria (ContidoCriteria cc, int startindex, int count)
			throws DataException;
	//Devolve unha lista de contidos seg�n un criterio de b�squeda.
	
	public List<? extends Contido> findTopN (int n, char tipo)
			throws DataException;
	//Devolve os n contidos mellor valorados polos usuarios.
	
	public void vota (Long idUsuario, Long idContido, Integer nota) 
			throws DataException;
	//M�todo para votaci�n de contidos
	
	public Contido create (Contido c)
		throws DataException; 
	//Creaci�n de playlists
	

}
