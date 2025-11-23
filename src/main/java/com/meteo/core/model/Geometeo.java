package com.meteo.core.model;

import java.util.Map;

public class Geometeo {
    private final String deviceId;
    private final String moduleId;
    private final String type;
    private final Map<Long, Double> measure;

	public Geometeo(String deviceId, String type, Map<Long, Double> measure) {
		this.deviceId = deviceId;
        this.moduleId = null;
		this.type = type;
		this.measure = measure;
    }

    public Geometeo(String deviceId, String moduleId, String type, Map<Long, Double> measure) {
		this.deviceId = deviceId;
        this.moduleId = moduleId;
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
                (moduleId != null ? ", moduleId='" + moduleId + '\'' : "") +
                ", type='" + type + '\'' +
                ", measure=" + measure +
                '}';
    }
}
