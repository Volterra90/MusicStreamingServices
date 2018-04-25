package com.musicstreaming.streaming.dao;

import java.sql.Connection;
import java.util.List;

import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.model.Contido;
import com.musicstreaming.streaming.service.ContidoCriteria;

/**
 * Interfaz do DAO de Contido.
 * @author Alberto Taboada Varela
 *
 */

public interface ContidoDAO {
	
	public Contido findById(Connection connection, Long id)
		throws InstanceNotFoundException, DataException;
	//Devolve un contido segundo a súa PK.
	
	public List<Contido> findByCriteria (Connection connection, int startIndex, int count, ContidoCriteria cc)
		throws DataException; 
	//Devolve unha lista de contidos según un criterio de búsqueda.
	
	public List<Contido> findTopN (int n, char tipo, Connection connection)
			throws DataException;
	//Devolve os n contidos mellor valorados polos usuarios.
	
	public void vota(Connection connection, Long idUsuario, Long idContido, Integer nota) 
		throws DataException;
	//Método para votación de contidos
	
	public Contido create(Connection connection, Contido c)
			throws InstanceNotFoundException, DataException;
	//Creación de playlists
}
