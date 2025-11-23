package com.meteo.geometeo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meteo.core.CoreMeteoService;

import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class GeometeoJob {
	private final CoreMeteoService coreMeteoService;
	private static final Logger LOG = LoggerFactory.getLogger(GeometeoJob.class);

	@Inject()
	public GeometeoJob(final CoreMeteoService coreMeteoService) {
		this.coreMeteoService = coreMeteoService;
		LOG.info("GeometeoJob created");
	}

	@Scheduled(fixedDelay = "${scheduler.delay}")
	void fetchMeasure() {
		LOG.info("Simple Job is triggered every 1 minutes: {}", new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()));
		// "NAMain", Living room
        coreMeteoService.fetchMeasure("70:ee:50:84:33:6a", "temperature", 1, 13);
		coreMeteoService.fetchMeasure("70:ee:50:84:33:6a", "pressure", 2, 13);
		coreMeteoService.fetchMeasure("70:ee:50:84:33:6a", "humidity", 3, 13);
		coreMeteoService.fetchMeasure("70:ee:50:84:33:6a", "CO2", 4, 13);
		coreMeteoService.fetchMeasure("70:ee:50:84:33:6a", "noise", 5, 13);

        // "NAModule1", "Outdoor"
        coreMeteoService.fetchMeasure("02:00:00:84:50:66", "temperature", 6, 13);
        coreMeteoService.fetchMeasure("02:00:00:84:50:66", "humidity", 7, 13);

        // "NAModule4", "Bedroom"
        coreMeteoService.fetchMeasure("03:00:00:07:c3:ee", "temperature", 8, 13);
        coreMeteoService.fetchMeasure("03:00:00:07:c3:ee", "humidity", 9, 13);
        coreMeteoService.fetchMeasure("03:00:00:07:c3:ee", "CO2", 10, 13);

        // "NAModule4", "Baby room"
        coreMeteoService.fetchMeasure("03:00:00:07:11:9c", "temperature", 11, 13);
        coreMeteoService.fetchMeasure("03:00:00:07:11:9c", "humidity", 12, 13);
        coreMeteoService.fetchMeasure("03:00:00:07:11:9c", "CO2", 13, 13);
	}
}
