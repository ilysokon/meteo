package com.meteo.netatmo;

import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;
import org.reactivestreams.Publisher;

import java.util.List;
import java.util.Map;

import static io.micronaut.http.HttpHeaders.ACCEPT;
import static io.micronaut.http.HttpHeaders.AUTHORIZATION;
import static io.micronaut.http.HttpHeaders.USER_AGENT;

@Client(id = "netatmo")
@Header(name = USER_AGENT, value = "Micronaut HTTP Client")
@Header(name = ACCEPT, value = "application/json")
@Header(name = AUTHORIZATION, value = "Bearer ${netatmo.token}")
public interface NetatmoApiClient {
    @Get("/api/getmeasure?device_id=70:ee:50:84:33:6a&scale=1day&type=temperature&type=humidity&type=co2&type=pressure&optimize=false&real_time=false")
    Publisher<String> fetchMeasurement();
}
