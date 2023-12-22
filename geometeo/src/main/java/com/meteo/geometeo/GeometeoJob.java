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
	private static final Logger LOG = LoggerFactory.getLogger(GeometeoJob.class);

	private final CoreMeteoService geometeoService;

	@Inject
	public GeometeoJob(final CoreMeteoService geometeoService) {
		this.geometeoService = geometeoService;
		LOG.info("GeometeoJob created");
	}

	@Scheduled(fixedDelay = "10s")
	public void executeEveryTen() {
		LOG.info("Simple Job every 10 seconds: {}", new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()));
		geometeoService.handleMeteo();
	}
}
