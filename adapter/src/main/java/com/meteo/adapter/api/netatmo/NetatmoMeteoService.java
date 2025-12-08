package com.meteo.adapter.api.netatmo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meteo.adapter.api.netatmo.model.MeteoResponse;
import com.meteo.application.ports.out.geometeo.GeometeoService;
import com.meteo.application.ports.out.persistence.PersistenceService;
import com.meteo.domain.model.Geometeo;
import io.reactivex.Flowable;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;

import javax.swing.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import static java.util.stream.Collectors.toMap;

@Singleton
public class NetatmoMeteoService implements GeometeoService {
	private static final Logger LOG = LoggerFactory.getLogger(NetatmoMeteoService.class);

    private final ObjectMapper objectMapper;

    private Instant beginDate;
    private Instant endDate;

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
        Instant endDate = getEndDate(requestNumber);
        Instant beginDate = getBeginDate();

        LOG.info("beginDate: " + beginDate + ", endDate: " + endDate);
        Flux.from(
             Flowable.fromPublisher(netatmoLowLevelGetMeasureApiClient.fetchMeasure(deviceId, type, beginDate.getEpochSecond(), endDate.getEpochSecond()))
                .map(meteoResponse -> mapToGeometeoData(requestNumber, targetRequestNumber, meteoResponse))
                .map(result -> new Geometeo(deviceId, type, beginDate, endDate, result.getBody().entrySet().stream()
                        .map(entry ->  Map.entry(entry.getKey(), entry.getValue().getFirst()))
                        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue))))
        ).subscribe(new ReactiveStreamsSubscriber(persistenceService, "no data in request: " + requestNumber + ", for deviceId " + deviceId));
    }



    public void fetchMeasureAndPersist(final String deviceId, final String moduleId, final String type, int requestNumber, int targetRequestNumber) {
		LOG.info("Getting meteo data from Netatmo Low Level Api client for deviceId: " + deviceId + ", modedelId: " + moduleId +  " and type: " + type);
        Instant endDate = getEndDate(requestNumber);
        Instant beginDate = getBeginDate();

        LOG.info("beginDate: " + beginDate + ", endDate: " + endDate);
        Flux.from(
              Flowable.fromPublisher(netatmoLowLevelGetMeasureApiClient.fetchMeasure(deviceId, moduleId, type, beginDate.getEpochSecond(), endDate.getEpochSecond()))
                .map(meteResponse -> mapToGeometeoData(requestNumber, targetRequestNumber, meteResponse))
                .map(result -> new Geometeo(deviceId, moduleId, type, beginDate, endDate, result.getBody().entrySet().stream()
                        .map(entry ->  Map.entry(entry.getKey(), entry.getValue().getFirst()))
                        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue))))
        ).subscribe(new ReactiveStreamsSubscriber(persistenceService, "no data in request: " + requestNumber + ", for deviceId " + deviceId));
	}

    private MeteoResponse mapToGeometeoData(int requestNumber, int targetRequestNumber, MeteoResponse meteoResponse) throws JsonProcessingException {
        if (meteoResponse.getBody() != null ) {
            if(requestNumber == targetRequestNumber) {
                LOG.info("all requests are done, requestNumber: " + requestNumber + " is reached the " + targetRequestNumber);
                beginDate = endDate;
                endDate = null;
            }
        }

        return meteoResponse;
    }

    private Instant getBeginDate() {
        if (beginDate == null) {
            beginDate = endDate.minusSeconds(netatmoConfiguration.measureScaleMinutes() * 60);
        }

        return beginDate;
    }

    private Instant getEndDate(int requestNumber) {
        if (endDate == null || requestNumber == 1) {
            endDate = getUnixLocalTimestamp();
        }

        return endDate;
    }

    private static Instant getUnixLocalTimestamp() {
        Date date = new Date();

        TimeZone timeZone =  TimeZone.getDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, timeZone.toZoneId());
        return zonedDateTime.toInstant();
    }
}
