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

import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

@Singleton
public class NetatmoMeteoService implements GeometeoService {
	private static final Logger LOG = LoggerFactory.getLogger(NetatmoMeteoService.class);
    @Inject
    ObjectMapper mapper;
    Long beginDate;
    Long endDate;
    private Integer currentRequestNumber;

    private final NetatmoGetMeasureApiClient netatmoGetMeasureApiClient;
    private final NetatmoLowLevelGetMeasureApiClient netatmoLowLevelGetMeasureApiClient;
    @Inject
    private NetatmoConfiguration netatmoConfiguration;

    public NetatmoMeteoService(final NetatmoGetMeasureApiClient netatmoGetMeasureApiClient, final NetatmoLowLevelGetMeasureApiClient netatmoLowLevelGetMeasureApiClient) {
        this.netatmoGetMeasureApiClient = netatmoGetMeasureApiClient;
        this.netatmoLowLevelGetMeasureApiClient = netatmoLowLevelGetMeasureApiClient;
        this.currentRequestNumber = 0;
        LOG.info("NetatmoMeteoService is created");
	}

	//@Override
	public Publisher<Map<Long, Double>> getMeteo2() {
		LOG.info("Getting meteo data from Netatmo Api Client ...");

        return Flowable.fromPublisher(netatmoGetMeasureApiClient.fetchMeasure())
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
	public Publisher<Map<Long, Double>> fetchMeasure(final String deviceId, final String type, int requestNumber, int allRequestNumbers) {
		LOG.info("Getting meteo data from Netatmo Low Level Api client for deviceId: " + deviceId + " and type: " + type);
        if (endDate == null || requestNumber == 1) {
            endDate = getUnixLocalTimestamp();
        }
        if (beginDate == null) {
            beginDate = endDate - netatmoConfiguration.measureScaleMinutes() * 60;
        }

        LOG.info("beginDate: " + beginDate + ", endDate: " + endDate);
        return Flowable.fromPublisher(netatmoLowLevelGetMeasureApiClient.fetchMeasure(deviceId, type, beginDate, endDate))
//                .retryWhen(errors ->
//                        errors.zipWith(
//                                Flowable.range(1, 3),
//                                (error, retryCount) -> retryCount
//                        ).flatMap(retryCount -> {
//                            LOG.warn("Retry #" + retryCount + " because: " + errors);
//                            return Flowable.timer(retryCount * 2, TimeUnit.SECONDS);
//                        })
//                )
                .map(json -> {
                    JsonNode root = mapper.readTree(json);
                    JsonNode bodyNode = root.get("body");
                    Map<Long, Double> result = new TreeMap<>();

                    if (!bodyNode.isEmpty()) {
                        bodyNode.fields().forEachRemaining(entry -> {
                            long ts = Long.parseLong(entry.getKey());
                            double value = entry.getValue().get(0).asDouble();
                            result.put(ts, value);
                        });
                        if(requestNumber == allRequestNumbers) {
                            beginDate = endDate;
                            endDate = null;
                        }
                        LOG.info("beginDate = endDate: " + endDate);
                    }

                    return result;
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
