package com.meteo.adapter.api.netatmo;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.retry.annotation.Retryable;
import org.reactivestreams.Publisher;

@Client(id = "netatmo")
@Header(name = HttpHeaders.USER_AGENT, value = "Micronaut HTTP Client")
@Header(name = HttpHeaders.ACCEPT, value = "application/json")
@Header(name = HttpHeaders.AUTHORIZATION, value = "Bearer ${netatmo.accessToken}")
@Retryable(
        attempts = "3",          // retry 3 times
        delay = "1s",            // 1 second between attempts
        multiplier = "2",        // exponential backoff
        includes = {HttpClientResponseException.class}
)
public interface NetatmoGetMeasureApiClient {
    @Get("/api/getmeasure?device_id=70:ee:50:84:33:6a&scale=30min&type=temperature&type=humidity&type=co2&type=pressure&optimize=false&real_time=false")
    Publisher<String> fetchMeasure();
}
