package com.musicstreaming.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.musicstreaming.model.Cancion;
import com.musicstreaming.model.Contido;
import com.musicstreaming.services.ContidoCriteria;
import com.musicstreaming.services.ContidoService;
import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;

/**
 * Ejemplo de implementacion mock. 
 *
 * @author https://www.linkedin.com/in/joseantoniolopezperez
 * @version 0.2
 */
public class ContidoMockServiceImpl implements ContidoService {
	
		
	public ContidoMockServiceImpl() {		
	}
	
	@Override
	public Contido findById(Long id)
			throws InstanceNotFoundException, DataException {
		
		Contido contido = create((int) id.longValue());
		return contido;
	}
	
	@Override
	public List<Contido> findByCriteria (ContidoCriteria cc)
			throws DataException {
		
		List<Contido> results = new ArrayList<Contido>();
		
		for (int i = 0; i<10; i++) {
			results.add(create(i));
		}
		return results;
			
	}
	
	private static Cancion create(int seed) {
		Cancion c = new Cancion();
		c.setCodContido(Long.valueOf(seed));
		c.setCodAutor(Long.valueOf(seed));
		c.setTipo('c');
		c.setNome("cancion "+seed);
		c.setDuracion(2304201230L);
		c.setCodEstilo(Long.valueOf(seed));
		return c;
	}
	
}