package com.meteo.application.ports.out.geometeo;

public interface GeometeoService {
    void fetchMeasureAndPersist(String deviceId, String type, int requestNumber, int allRequestNumbers);
    void fetchMeasureAndPersist(String deviceId, String moduleid, String type, int requestNumber, int allRequestNumbers);
}
