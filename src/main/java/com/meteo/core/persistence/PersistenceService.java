package com.meteo.core.persistence;


import com.meteo.core.model.Geometeo;

public interface PersistenceService {
	public void store(Geometeo geometeo);
}
