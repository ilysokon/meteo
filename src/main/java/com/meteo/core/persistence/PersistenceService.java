package com.meteo.core.persistence;


import com.meteo.core.model.Geometeo;

public interface PersistenceService {
	void store(Geometeo geometeo);
}
