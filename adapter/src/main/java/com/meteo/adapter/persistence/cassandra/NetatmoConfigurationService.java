package com.meteo.adapter.persistence.cassandra;

import com.meteo.adapter.api.netatmo.NetatmoConfiguration;
import com.meteo.application.ports.out.persistence.PersistenceService;
import jakarta.inject.Singleton;

@Singleton
public class NetatmoConfigurationService {
    private final NetatmoConfiguration netatmoConfiguration;
    private final PersistenceService persistenceService;
    private NetatmoConfiguration updatedConfiguration;

    public NetatmoConfigurationService(NetatmoConfiguration netatmoConfiguration, final PersistenceService persistenceService) {
        this.netatmoConfiguration = netatmoConfiguration;
        this.persistenceService = persistenceService;
    }

    public synchronized NetatmoConfiguration getNetatmoConfiguration() {
        if (updatedConfiguration == null) {
            return netatmoConfiguration;
        }

        return updatedConfiguration;
    }

    public synchronized void updateConfiguration() {
        updatedConfiguration = new NetatmoConfiguration(persistenceService.getGeometeoToken().accessToken(), netatmoConfiguration.measureScaleMinutes());
        LOG.info("Netatmo Configuration is updated by new access token.")
    }
}
