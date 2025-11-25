package com.meteo.application.ports.out.geometeo;

import org.reactivestreams.Publisher;

import java.util.Map;

public interface GeometeoService {
    Publisher<Map<Long, Double>> fetchMeasure(String deviceId, String type, int requestNumber, int allRequestNumbers);
    Publisher<Map<Long, Double>> fetchMeasure(String deviceId, String moduleid, String type, int requestNumber, int allRequestNumbers);
}
