package com.meteo.application.ports.out.persistence;


import com.meteo.domain.model.Geometeo;

public interface PersistenceService {
	void store(Geometeo geometeo);
}
