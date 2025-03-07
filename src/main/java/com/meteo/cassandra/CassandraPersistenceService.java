package com.meteo.cassandra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meteo.core.model.Geometeo;
import com.meteo.core.persistence.PersistenceService;

import jakarta.inject.Singleton;

@Singleton
public class CassandraPersistenceService implements PersistenceService {
	private static final Logger LOG = LoggerFactory.getLogger(CassandraPersistenceService.class);

	public CassandraPersistenceService() {
		LOG.info("CassandraPersistenceService is created");
	}

	@Override
	public void store(final Geometeo geometeo) {
		LOG.info("Geometeo: " + geometeo + " is persisted in Cassandra");
	}
}
