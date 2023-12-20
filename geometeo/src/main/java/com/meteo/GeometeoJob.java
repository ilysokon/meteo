package com.meteo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;

@Singleton
public class GeometeoJob {
	private static final Logger LOG = LoggerFactory.getLogger(GeometeoJob.class);

	public GeometeoJob() {
		LOG.info("GeometeoJob created");
	}

	@Scheduled(fixedDelay = "10s")
	void executeEveryTen() {
		LOG.info("Simple Job every 10 seconds: {}", new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()));
	}

}
