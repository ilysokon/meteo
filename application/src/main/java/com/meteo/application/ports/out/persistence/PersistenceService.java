package com.meteo.application.ports.out.persistence;


import com.meteo.domain.model.Geometeo;
import com.meteo.domain.model.GeometeoToken;

public interface PersistenceService {
	void store(Geometeo geometeo);
    GeometeoToken getGeometeoToken();
}
