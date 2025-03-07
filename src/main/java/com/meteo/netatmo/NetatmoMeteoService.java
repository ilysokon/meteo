package com.meteo.netatmo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meteo.core.geometeo.GeometeoService;
import com.meteo.core.model.Geometeo;

import jakarta.inject.Singleton;

@Singleton
public class NetatmoMeteoService implements GeometeoService {
	private static final Logger LOG = LoggerFactory.getLogger(NetatmoMeteoService.class);

	public NetatmoMeteoService() {
		LOG.info("NetatmoMeteoService is created");
	}

	@Override
	public Geometeo getMeteo() {
		LOG.info("Getting meteo data from Netatmo serveer ...");

		LOG.info("Meteo data from Netatmo serveer are got");
		return new Geometeo("Netatmo");
	}
}
