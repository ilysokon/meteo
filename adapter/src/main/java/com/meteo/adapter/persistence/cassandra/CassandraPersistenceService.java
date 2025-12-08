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
    private static final Map<String, Integer> CASSANDRA_NODES = Map.of(
            "cassandra-0-0.cassandra.meteo.svc.cluster.local", 9042,
            "cassandra-1-0.cassandra.meteo.svc.cluster.local", 9042
//            "localhost", 9042
    );
    private GeometeoCqlRepository  geometeoCqlRepository;

	public CassandraPersistenceService() {
        final var cassandraConnector = new CassandraConnector();
        cassandraConnector.connect(CASSANDRA_NODES, "dc1");
        geometeoCqlRepository = new GeometeoCqlRepository(cassandraConnector.getSession(), "meteo");
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
