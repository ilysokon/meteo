package com.meteo.netatmo;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;

@ConfigurationProperties(NetatmoConfiguration.PREFIX)
@Requires(property = NetatmoConfiguration.PREFIX)
public record NetatmoConfiguration(String token) {
    public static final String PREFIX = "netatmo";
}
