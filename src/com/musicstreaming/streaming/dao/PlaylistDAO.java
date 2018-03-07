package com.musicstreaming.streaming.dao;

import java.sql.Connection;

import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.exceptions.InstanceNotFoundException;
import com.musicstreaming.streaming.model.Playlist;

public interface PlaylistDAO {
	
	public Playlist findById(Connection connection,Long id) 
			throws InstanceNotFoundException, DataException;
	
}
