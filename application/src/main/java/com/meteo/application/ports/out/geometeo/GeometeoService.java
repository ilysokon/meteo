package com.meteo.application.ports.out.geometeo;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface GeometeoService {
    void fetchMeasureAndPersist(String deviceId, String type, int requestNumber, int allRequestNumbers) throws JsonProcessingException;
    void fetchMeasureAndPersist(String deviceId, String moduleid, String type, int requestNumber, int allRequestNumbers) throws JsonProcessingException;
}
