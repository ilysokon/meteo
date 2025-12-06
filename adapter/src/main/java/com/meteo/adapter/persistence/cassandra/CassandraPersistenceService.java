package com.meteo.adapter.persistence.cassandra;

import com.meteo.persistence.GeometeoCqlRepository;
import com.simba.cassandra.shaded.datastax.driver.core.utils.UUIDs;
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
    private static final Map<String, Integer> CASSANDRA_NODES = Map.of(
            "192.168.2.42", 9042,
            "192.168.2.43", 9042,
            "192.168.2.44", 9042
    );
    private GeometeoCqlRepository  geometeoCqlRepository;

	public CassandraPersistenceService() {
        final var cassandraConnector = new CassandraConnector();
        cassandraConnector.connect(CASSANDRA_NODES, "datacenter1");
        geometeoCqlRepository = new GeometeoCqlRepository(cassandraConnector.getSession(), "meteo");
        System.out.println("Successfully connected to cassandra: " + cassandraConnector.getSession().toString());
		LOG.info("CassandraPersistenceService is created");
	}

	@Override
	public void store(final Geometeo geometeo) {
        final UUID uuid = UUIDs.timeBased();
        geometeoCqlRepository.insertGeometeo(geometeo,  uuid);
		LOG.info("Geometeo: " + geometeo + " is persisted in Cassandra");
	}
}
