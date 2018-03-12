package com.musicstreaming.streaming.service;

import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.model.Album;

public interface AlbumService {

	public Album findById(Long id) 
			throws InstanceNotFoundException, DataException;
	
}
