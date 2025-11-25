package com.meteo.adapter.persistence.cassandra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meteo.domain.model.Geometeo;
import com.meteo.application.ports.out.persistence.PersistenceService;

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
