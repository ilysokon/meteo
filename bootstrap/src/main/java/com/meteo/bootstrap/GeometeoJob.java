package com.meteo.bootstrap;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bettercloud.vault.VaultException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.meteo.adapter.api.netatmo.NetatmoMeteoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class GeometeoJob {
	private final NetatmoMeteoService netatmoMeteoService;
	private static final Logger LOG = LoggerFactory.getLogger(GeometeoJob.class);

	@Inject()
	public GeometeoJob(final NetatmoMeteoService netatmoMeteoService) {
		this.netatmoMeteoService = netatmoMeteoService;
		LOG.info("GeometeoJob created");
	}

	@Scheduled(fixedDelay = "${scheduler.delay}")
	void fetchMeasure() throws VaultException, JsonProcessingException {
		LOG.info("Simple Job is triggered every 1 minutes: {}", new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()));
        String room = System.getenv("ROOM_NAME");
        switch(room) {
            case "LIVINGROOM" ->  {
                // "NAMain", Living room
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a", "temperature", 1, 13);
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a", "pressure", 2, 13);
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a", "humidity", 3, 13);
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a", "CO2", 4, 13);
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a", "noise", 5, 13);
            } case "OUTDOOR" -> {
                // "NAModule1", "Outdoor"
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a","02:00:00:84:50:66", "temperature", 6, 13);
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a","02:00:00:84:50:66", "humidity", 7, 13);
            } case "BEDROOM" -> {
                // "NAModule4", "Bedroom"
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a", "03:00:00:07:c3:ee", "temperature", 8, 13);
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a", "03:00:00:07:c3:ee", "humidity", 9, 13);
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a", "03:00:00:07:c3:ee", "CO2", 10, 13);
            } case "BABYROOM" -> {
                // "NAModule4", "Baby room"
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a", "03:00:00:07:11:9c", "temperature", 11, 13);
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a", "03:00:00:07:11:9c", "humidity", 12, 13);
                netatmoMeteoService.fetchMeasureAndPersist("70:ee:50:84:33:6a", "03:00:00:07:11:9c", "CO2", 13, 13);
            }
        }
	}
}
