package com.meteo.adapter.api.netatmo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meteo.domain.model.Geometeo;
import io.reactivex.Flowable;
import jakarta.inject.Inject;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Singleton;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@Singleton
public class NetatmoMeteoService {
	private static final Logger LOG = LoggerFactory.getLogger(NetatmoMeteoService.class);

    private final ObjectMapper objectMapper;

    private Long beginDate;
    private Long endDate;

    private final NetatmoGetMeasureApiClient netatmoGetMeasureApiClient;
    private final NetatmoLowLevelGetMeasureApiClient netatmoLowLevelGetMeasureApiClient;
    @Inject
    private NetatmoConfiguration netatmoConfiguration;

    public NetatmoMeteoService(final NetatmoGetMeasureApiClient netatmoGetMeasureApiClient, final NetatmoLowLevelGetMeasureApiClient netatmoLowLevelGetMeasureApiClient, final ObjectMapper objectMapper) {
        this.netatmoGetMeasureApiClient = netatmoGetMeasureApiClient;
        this.netatmoLowLevelGetMeasureApiClient = netatmoLowLevelGetMeasureApiClient;
        this.objectMapper = objectMapper;

        LOG.info("NetatmoMeteoService is created");
	}

	//@Override
	public Publisher<Map<Long, Double>> getMeteo2() {
		LOG.info("Getting meteo data from Netatmo Api Client ...");

        return Flowable.fromPublisher(netatmoGetMeasureApiClient.fetchMeasure())
                .map(json -> {
                    JsonNode root = objectMapper.readTree(json);
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

    public Publisher<Geometeo> fetchMeasure(final String deviceId, final String type, int requestNumber, int targetRequestNumber) {
		LOG.info("Getting meteo data from Netatmo Low Level Api client for deviceId: " + deviceId + " and type: " + type);
        if (endDate == null || requestNumber == 1) {
            endDate = getUnixLocalTimestamp();
        }
        if (beginDate == null) {
            beginDate = endDate - netatmoConfiguration.measureScaleMinutes() * 60;
        }

        LOG.info("beginDate: " + beginDate + ", endDate: " + endDate);
        return Flowable.fromPublisher(netatmoLowLevelGetMeasureApiClient.fetchMeasure(deviceId, type, beginDate, endDate))
                .map(json -> {
                    JsonNode root = objectMapper.readTree(json);
                    JsonNode bodyNode = root.get("body");
                    Map<Long, Double> result = new TreeMap<>();

                    if (!bodyNode.isEmpty()) {
                        bodyNode.fields().forEachRemaining(entry -> {
                            long ts = Long.parseLong(entry.getKey());
                            double value = entry.getValue().get(0).asDouble();
                            result.put(ts, value);
                        });
                        if(requestNumber == targetRequestNumber) {
                            LOG.info("all requests are done, requestNumber: " + requestNumber + " is reached the " + targetRequestNumber);
                            beginDate = endDate;
                            endDate = null;
                        }
                    }

                    return new Geometeo(deviceId, type, result);
                });
	}

    public Publisher<Geometeo> fetchMeasure(final String deviceId, final String moduleId, final String type, int requestNumber, int targetRequestNumber) {
		LOG.info("Getting meteo data from Netatmo Low Level Api client for deviceId: " + deviceId + " and type: " + type);
        if (endDate == null || requestNumber == 1) {
            endDate = getUnixLocalTimestamp();
        }
        if (beginDate == null) {
            beginDate = endDate - netatmoConfiguration.measureScaleMinutes() * 60;
        }

        LOG.info("beginDate: " + beginDate + ", endDate: " + endDate);
        return Flowable.fromPublisher(netatmoLowLevelGetMeasureApiClient.fetchMeasure(deviceId, moduleId, type, beginDate, endDate))
                .map(json -> {
                    JsonNode root = objectMapper.readTree(json);
                    JsonNode bodyNode = root.get("body");
                    Map<Long, Double> result = new TreeMap<>();

                    if (!bodyNode.isEmpty()) {
                        bodyNode.fields().forEachRemaining(entry -> {
                            long ts = Long.parseLong(entry.getKey());
                            double value = entry.getValue().get(0).asDouble();
                            result.put(ts, value);
                        });
                        if(requestNumber == targetRequestNumber) {
                            LOG.info("all requests are done, requestNumber: " + requestNumber + " is reached the " + targetRequestNumber);
                            beginDate = endDate;
                            endDate = null;
                        }
                    }

                    return new Geometeo(deviceId, moduleId, type, result);
                });
	}

    private static long getUnixLocalTimestamp() {
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        // Timestamp → local unix time (seconds)
        long unixLocalSeconds = ts.toInstant()
                .atZone(ZoneId.systemDefault())
                .toEpochSecond();
        return unixLocalSeconds;
    }
}
