package com.meteo.core.model;

import java.util.Map;

public class Geometeo {
    private final String deviceId;
    private final String type;
    private final Map<Long, Double> measure;

	public Geometeo(String deviceId, String type, Map<Long, Double> measure) {
		this.deviceId = deviceId;
		this.type = type;
		this.measure = measure;
    }

	public Map<Long, Double> getMeasure() {
		return measure;
	}

    @Override
    public String toString() {
        return "Geometeo{" +
                "deviceId='" + deviceId + '\'' +
                ", type='" + type + '\'' +
                ", measure=" + measure +
                '}';
    }
}
