package com.meteo.core.model;

import java.util.HashMap;
import java.util.Map;

public class Geometeo {
	private final Map<Long, Double> measure;

    public Geometeo() {
       measure = new HashMap<>();
    }

	public Geometeo(Map<Long, Double> measure) {
		this.measure = measure;
	}

	public Map<Long, Double> getMeasure() {
		return measure;
	}

    public void add(Map<Long, Double> measure) {
        this.measure.putAll(measure);
    }

    @Override
    public String toString() {
        return measure.toString();
    }
}
