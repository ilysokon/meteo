package com.meteo.core.geometeo;

import com.meteo.core.model.Geometeo;
import org.reactivestreams.Publisher;

import java.util.Map;

public interface GeometeoService {
    Publisher<Map<Long, Double>> getMeteo();
}
