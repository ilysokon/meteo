package com.meteo.netatmo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Flowable;
import jakarta.inject.Inject;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meteo.core.geometeo.GeometeoService;

import jakarta.inject.Singleton;

import java.util.Map;
import java.util.TreeMap;

@Singleton
public class NetatmoMeteoService implements GeometeoService {
	private static final Logger LOG = LoggerFactory.getLogger(NetatmoMeteoService.class);
    @Inject
    ObjectMapper mapper;

    private final NetatmoApiClient netatmoApiClient;
    private final NetatmoLowLevelApiClient netatmoLowLevelApiClient;

	public NetatmoMeteoService(final NetatmoApiClient netatmoApiClient, final NetatmoLowLevelApiClient netatmoLowLevelApiClient) {
        this.netatmoApiClient = netatmoApiClient;
        this.netatmoLowLevelApiClient = netatmoLowLevelApiClient;

        LOG.info("NetatmoMeteoService is created");
	}

	//@Override
	public Publisher<Map<Long, Double>> getMeteo2() {
		LOG.info("Getting meteo data from Netatmo Api Client ...");

        return Flowable.fromPublisher(netatmoApiClient.fetchMeasure())
                .map(json -> {
                    JsonNode root = mapper.readTree(json);
                    JsonNode bodyNode = root.get("body");

                    Map<Long, Double> result = new TreeMap<>();
                    bodyNode.fields().forEachRemaining(entry -> {
                        long ts = Long.parseLong(entry.getKey());
                        double value = entry.getValue().get(0).asDouble();
                        result.put(ts, value);
                    });

                    return result;
                });
	}

    @Override
	public Publisher<Map<Long, Double>> getMeteo() {
		LOG.info("Getting meteo data from Netatmo Low Level Api client ...");

        return Flowable.fromPublisher(netatmoLowLevelApiClient.fetchMeasure())
                .map(json -> {
                    JsonNode root = mapper.readTree(json);
                    JsonNode bodyNode = root.get("body");

                    Map<Long, Double> result = new TreeMap<>();
                    bodyNode.fields().forEachRemaining(entry -> {
                        long ts = Long.parseLong(entry.getKey());
                        double value = entry.getValue().get(0).asDouble();
                        result.put(ts, value);
                    });

                    return result;
                });
	}
}
