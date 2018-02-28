package com.musicstreaming.dao.impl;

import java.sql.Connection;

import com.musicstreaming.dao.CancionDAO;
import com.musicstreaming.dao.Resultset;
import com.musicstreaming.model.Cancion;

public class CancionDAOImpl extends ContidoDAOImpl implements CancionDAO {

	public Cancion findById(Connection c, long id) throws ... {

		sql = Select id, nombre, duracion, ..,  // atriobuos comunes y propios
				from contenido cd, cancion cn 
				on cd.idContenido = cn.idContenido;
		
		resultset rs = ...;
		
		Cancion c = loadNext(rs)
		return c;
	}

	
	protected Cancion loadNext(Resultset rs) throws ... {
			
		// Creo el tipo especifico
		Cancion c = new Cancion();
		
		// El DAO padre carga los atirbutos comunes
		super.loadNext(rs, c);
		
		// Y carga los suyos propios
		c.setCancionAtiobutoProio(rs.getCancionAtirbutoproio);
		c.setCancionAtiobutoProio(rs.getCancionAtirbutoproio);
		c.setCancionAtiobutoProio(rs.getCancionAtirbutoproio);
		c.setCancionAtiobutoProio(rs.getCancionAtirbutoproio);
				
		return c;
	}

}
