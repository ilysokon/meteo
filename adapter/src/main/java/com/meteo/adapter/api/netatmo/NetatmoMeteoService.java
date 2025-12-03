package com.meteo.adapter.api.netatmo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meteo.application.ports.out.geometeo.GeometeoService;
import com.meteo.application.ports.out.persistence.PersistenceService;
import com.meteo.domain.model.Geometeo;
import io.reactivex.Flowable;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@Singleton
public class NetatmoMeteoService implements GeometeoService {
	private static final Logger LOG = LoggerFactory.getLogger(NetatmoMeteoService.class);

    private final ObjectMapper objectMapper;

    private Long beginDate;
    private Long endDate;

    private final NetatmoGetMeasureApiClient netatmoGetMeasureApiClient;
    private final NetatmoLowLevelGetMeasureApiClient netatmoLowLevelGetMeasureApiClient;
    private final PersistenceService persistenceService;

    @Inject
    private NetatmoConfiguration netatmoConfiguration;

    public NetatmoMeteoService(final NetatmoGetMeasureApiClient netatmoGetMeasureApiClient, final NetatmoLowLevelGetMeasureApiClient netatmoLowLevelGetMeasureApiClient, final PersistenceService persistenceService, final ObjectMapper objectMapper) {
        this.netatmoGetMeasureApiClient = netatmoGetMeasureApiClient;
        this.netatmoLowLevelGetMeasureApiClient = netatmoLowLevelGetMeasureApiClient;
        this.persistenceService = persistenceService;
        this.objectMapper = objectMapper;

        LOG.info("NetatmoMeteoService is created");
	}

	public void fetchMeasureAndPersist(final String deviceId, final String type, int requestNumber, int targetRequestNumber) {
		LOG.info("Getting meteo data from Netatmo Low Level Api client for deviceId: " + deviceId + " and type: " + type);
        initializeEndDate(requestNumber);
        initializeBeginDate();

        LOG.info("beginDate: " + beginDate + ", endDate: " + endDate);
        Flux.from(
             Flowable.fromPublisher(netatmoLowLevelGetMeasureApiClient.fetchMeasure(deviceId, type, beginDate, endDate))
                .map(json -> mapToGeometeoData(requestNumber, targetRequestNumber, json))
                .map(result -> new Geometeo(deviceId, type, result))
        ).subscribe(new ReactiveStreamsSubscriber(persistenceService, "no data in request: " + requestNumber + ", for deviceId " + deviceId));
    }



    public void fetchMeasureAndPersist(final String deviceId, final String moduleId, final String type, int requestNumber, int targetRequestNumber) {
		LOG.info("Getting meteo data from Netatmo Low Level Api client for deviceId: " + deviceId + ", modedelId: " + moduleId +  " and type: " + type);
        initializeEndDate(requestNumber);
        initializeBeginDate();

        LOG.info("beginDate: " + beginDate + ", endDate: " + endDate);
        Flux.from(
              Flowable.fromPublisher(netatmoLowLevelGetMeasureApiClient.fetchMeasure(deviceId, moduleId, type, beginDate, endDate))
                .map(json -> mapToGeometeoData(requestNumber, targetRequestNumber, json))
                .map(result -> new Geometeo(deviceId, moduleId, type, result))
        ).subscribe(new ReactiveStreamsSubscriber(persistenceService, "no data in request: " + requestNumber + ", for deviceId " + deviceId));
	}

    private Map<Long, Double> mapToGeometeoData(int requestNumber, int targetRequestNumber, String json) throws JsonProcessingException {
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

        return result;
    }

    private void initializeBeginDate() {
        if (beginDate == null) {
            beginDate = endDate - netatmoConfiguration.measureScaleMinutes() * 60;
        }
    }

    private void initializeEndDate(int requestNumber) {
        if (endDate == null || requestNumber == 1) {
            endDate = getUnixLocalTimestamp();
        }
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
