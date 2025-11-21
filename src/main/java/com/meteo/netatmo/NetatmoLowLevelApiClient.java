package com.meteo.netatmo;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

import java.net.URI;

import static io.micronaut.http.HttpHeaders.ACCEPT;
import static io.micronaut.http.HttpHeaders.AUTHORIZATION;
import static io.micronaut.http.HttpHeaders.USER_AGENT;

@Singleton
public class NetatmoLowLevelApiClient {
    private final HttpClient httpClient;
    private final URI uri;
    private final NetatmoConfiguration configuration;

    public NetatmoLowLevelApiClient(@Client(id = "netatmo") HttpClient httpClient,
                                    NetatmoConfiguration configuration) {
        this.httpClient = httpClient;
        uri = UriBuilder.of("/api")
                .queryParam("device_id", "70:ee:50:84:33:6a")
                .queryParam("scale", "1day")
                .queryParam("type", "temperature")
                .queryParam("optimize", "false")
                .queryParam("real_time", "false")
                .path("getmeasure")
                .build();
        this.configuration = configuration;
    }

    Publisher<String> fetchMeasure() {
        HttpRequest<?> req = HttpRequest.GET(uri)
                .header(USER_AGENT, "Micronaut HTTP Client")
                .header(ACCEPT, "application/json")
                .header(AUTHORIZATION, "Bearer " + configuration.token());
        return httpClient.retrieve(req, String.class);
    }
}
