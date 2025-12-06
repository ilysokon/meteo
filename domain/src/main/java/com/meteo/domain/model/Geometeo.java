package com.meteo.domain.model;

import java.util.Map;

public class Geometeo {
    private final String deviceId;
    private final String moduleId;
    private final String type;
    private Long  beginDate;
    private Long endDate;
    private final Map<Long, Double> measure;

	public Geometeo(String deviceId, String type, Long beginDate, Long endDate, Map<Long, Double> measure) {
		this.deviceId = deviceId;
        this.moduleId = null;
		this.type = type;
        this.beginDate = beginDate;
        this.endDate = endDate;
		this.measure = measure;
    }

    public Geometeo(String deviceId, String moduleId, String type, Long beginDate, Long endDate, Map<Long, Double> measure) {
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

    public Long getBeginDate() {
        return beginDate;
    }

    public Long getEndDate() {
        return endDate;
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
