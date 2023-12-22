package com.meteo.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meteo.core.geometeo.GeometeoService;
import com.meteo.core.persistence.PersistenceService;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class CoreMeteoService {
	private static final Logger LOG = LoggerFactory.getLogger(CoreMeteoService.class);

	private final GeometeoService geometeoService;
	private final PersistenceService persistenceService;

	@Inject
	public CoreMeteoService(final GeometeoService geometeoService, final PersistenceService persistenceService) {
		this.geometeoService = geometeoService;
		this.persistenceService = persistenceService;

		LOG.info("CoreMeteoService created");
	}

	public void handleMeteo() {
		final var meteo = geometeoService.getMeteo();
		persistenceService.store(meteo);
	}
}
