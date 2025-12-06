package com.meteo.adapter.persistence.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.stream.Collectors;

class CassandraConnector {

    private CqlSession session;

    void connect(final Map<String, Integer> nodes, final String dataCenter) {
        session = CqlSession.builder()
             .addContactPoints(nodes.entrySet().stream()
                     .map(node -> new InetSocketAddress(node.getKey(), node.getValue()))
                     .collect(Collectors.toList()))
            .withLocalDatacenter(dataCenter)
            .build();
    }

    CqlSession getSession() {
        return this.session;
    }

    void close() {
        session.close();
    }
}
