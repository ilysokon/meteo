package com.meteo.adapter.persistence.cassandra;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.meteo.persistence.GeometeoCqlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meteo.domain.model.Geometeo;
import com.meteo.application.ports.out.persistence.PersistenceService;

import jakarta.inject.Singleton;

import java.util.Map;
import java.util.UUID;

@Singleton
public class CassandraPersistenceService implements PersistenceService {
	private static final Logger LOG = LoggerFactory.getLogger(CassandraPersistenceService.class);

    private GeometeoCqlRepository  geometeoCqlRepository;

	public CassandraPersistenceService(final CassandraConfiguration cassandraConfiguration) {
        final var cassandraConnector = new CassandraConnector();
        cassandraConnector.connect(cassandraConfiguration.contactPoints(), cassandraConfiguration.dataCenter());
        geometeoCqlRepository = new GeometeoCqlRepository(cassandraConnector.getSession(), cassandraConfiguration.keyspace());
        System.out.println("Successfully connected to cassandra: " + cassandraConnector.getSession().toString());
		LOG.info("CassandraPersistenceService is created");
	}

	@Override
	public void store(final Geometeo geometeo) {
        final UUID uuid = Uuids.timeBased();
        geometeoCqlRepository.insertGeometeo(geometeo,  uuid);
		LOG.info("Geometeo: " + geometeo + " is persisted in Cassandra");
	}
}
