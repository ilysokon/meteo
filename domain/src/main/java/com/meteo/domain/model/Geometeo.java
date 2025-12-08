package com.meteo.domain.model;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class Geometeo {
    private final String deviceId;
    private final String moduleId;
    private final String type;
    private final Instant  beginDate;
    private final Instant endDate;
    private final Map<Instant, Double> measure;

	public Geometeo(String deviceId, String type, Instant beginDate, Instant endDate, Map<Instant, Double> measure) {
		this.deviceId = deviceId;
        this.moduleId = deviceId;
		this.type = type;
        this.beginDate = beginDate;
        this.endDate = endDate;
		this.measure = measure;
    }

    public Geometeo(String deviceId, String moduleId, String type, Instant beginDate, Instant endDate, Map<Instant, Double> measure) {
		this.deviceId = deviceId;
        this.moduleId = moduleId;
        this.type = type;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.measure = measure;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public String getType() {
        return type;
    }

    public Instant getBeginDate() {
        return beginDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public Map<Instant, Double> getMeasure() {
		return measure;
	}

    @Override
    public String toString() {
        return "Geometeo{" +
                "deviceId='" + deviceId + '\'' +
                ", moduleId='" + moduleId + '\'' +
                ", type='" + type + '\'' +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", measure=" + measure +
                '}';
    }
}
