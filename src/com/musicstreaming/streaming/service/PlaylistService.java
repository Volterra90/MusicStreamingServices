package com.musicstreaming.streaming.service;

import java.util.List;

import com.musicstreaming.streaming.exceptions.DataException;
import com.musicstreaming.streaming.model.Playlist;

public interface PlaylistService {
	
	public List<Playlist> findByUsuario(Long idUsuario)
			throws DataException;

}
