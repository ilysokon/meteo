package com.meteo.bootstrap;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.meteo.adapter.api.netatmo.NetatmoMeteoService;
import com.meteo.adapter.persistence.cassandra.NetatmoConfigurationService;
import io.micronaut.context.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class GeometeoJob {
	private final NetatmoMeteoService netatmoMeteoService;
    private final NetatmoConfigurationService netatmoConfigurationService;
    private final String delay;
	private static final Logger LOG = LoggerFactory.getLogger(GeometeoJob.class);

	@Inject()
	public GeometeoJob(final NetatmoMeteoService netatmoMeteoService, final NetatmoConfigurationService netatmoConfigurationService, @Value("${scheduler.geometeo.measure.delay}") String delay) {
		this.netatmoMeteoService = netatmoMeteoService;
        this.netatmoConfigurationService = netatmoConfigurationService;
        this.delay = delay;
        LOG.info("GeometeoJob created");
	}

	@Scheduled(fixedDelay = "${scheduler.geometeo.measure.delay}")
	void fetchMeasure() {
		LOG.info("Simple Job is triggered every {} minutes: {}", delay, new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()));
        String room = System.getenv("ROOM_NAME");
        switch(room) {
            case "LIVINGROOM" ->  {
                // "NAMain", Living room
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a", "temperature", 1, 5);
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a", "pressure", 2, 5);
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a", "humidity", 3, 5);
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a", "CO2", 4, 5);
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a", "noise", 5, 5);
            } case "OUTDOOR" -> {
                // "NAModule1", "Outdoor"
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a","02:00:00:84:50:66", "temperature", 1, 2);
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a","02:00:00:84:50:66", "humidity", 2, 2);
            } case "BEDROOM" -> {
                // "NAModule4", "Bedroom"
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a", "03:00:00:07:c3:ee", "temperature", 1, 3);
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a", "03:00:00:07:c3:ee", "humidity", 2, 3);
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a", "03:00:00:07:c3:ee", "CO2", 3, 3);
            } case "BABYROOM" -> {
                // "NAModule4", "Baby room"
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a", "03:00:00:07:11:9c", "temperature", 1, 3);
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a", "03:00:00:07:11:9c", "humidity", 2, 3);
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a", "03:00:00:07:11:9c", "CO2", 3, 3);
            }
        }
	}

    /**
     * Only for testing purpose.
     */
//    @Scheduled(fixedDelay = "${scheduler.geometeo.token.delay}")
//    void updateGeometeoTokenIfNecessary() {
//        netatmoConfigurationService.updateConfiguration();
//
//        LOG.info("updateGeometeoTokenIfNecessary() Job is triggered every 30 minutes: {}", new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()));
//    }
}
