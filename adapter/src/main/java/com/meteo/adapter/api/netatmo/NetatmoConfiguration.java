package com.meteo.adapter.api.netatmo;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

@ConfigurationProperties(NetatmoConfiguration.PREFIX)
@Requires(property = NetatmoConfiguration.PREFIX)
public record NetatmoConfiguration(String accessToken, Integer measureScaleMinutes) {
    private static final Logger LOG = getLogger(NetatmoConfiguration.class);

    public static final String PREFIX = "netatmo";

    public NetatmoConfiguration {
        if (accessToken == null) {
            throw new IllegalArgumentException("accessToken cannot be null");
        }

        LOG.info("Netatmo Configuration accessToken: {}", accessToken);
    }
}
