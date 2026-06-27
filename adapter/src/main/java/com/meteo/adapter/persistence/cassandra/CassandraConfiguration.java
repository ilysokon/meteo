package com.meteo.adapter.persistence.cassandra;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;

import java.util.Map;

@ConfigurationProperties(CassandraConfiguration.PREFIX)
@Requires(property = CassandraConfiguration.PREFIX)
public record CassandraConfiguration(String keyspace, String dataCenter, Map<String, Integer> contactPoints) {
    public static final String PREFIX = "cassandra";
}
