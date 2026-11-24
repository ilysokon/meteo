package com.meteo.adapter.kafka;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;

import java.util.List;

@ConfigurationProperties(KafkaConfiguration.PREFIX)
@Requires(property = KafkaConfiguration.PREFIX)
public record KafkaConfiguration(String bootstrapServers, List<String> meteoSourceTopicNames) {
    public static final String PREFIX = "kafka";
}
