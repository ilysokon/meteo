package com.meteo.adapter.api.netatmo;

import com.meteo.adapter.api.netatmo.model.MeteoResponse;
import com.meteo.adapter.persistence.cassandra.NetatmoConfigurationService;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

import java.net.URI;

@Singleton
public class NetatmoLowLevelGetMeasureApiClient {
    private final HttpClient httpClient;
    private final URI uri;
    private final NetatmoConfigurationService configurationService;

    public NetatmoLowLevelGetMeasureApiClient(@Client(id = "netatmo") HttpClient httpClient,
                                              NetatmoConfigurationService configurationService) {
        this.httpClient = httpClient;
        uri = UriBuilder.of("/api")
                .queryParam("scale", "30min")
                .queryParam("optimize", "false")
                .queryParam("real_time", "true")
                .path("getmeasure")
                .build();
        this.configurationService = configurationService;
    }

    Publisher<MeteoResponse> fetchMeasure(String deviceId, String type, Long beginDate, Long endDate) {
        var uri = UriBuilder.of(this.uri)
                .queryParam("device_id", deviceId)
                .queryParam("type", type)
                .queryParam("date_begin", beginDate)
                .queryParam("date_end", endDate)
                .build();
        HttpRequest<?> req = HttpRequest.GET(uri)
                .header(HttpHeaders.USER_AGENT, "Micronaut HTTP Client")
                .header(HttpHeaders.ACCEPT, "application/json")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + configurationService.getNetatmoConfiguration().accessToken());
        return httpClient.retrieve(req, MeteoResponse.class);
    }

    Publisher<MeteoResponse> fetchMeasure(String deviceId, String moduleId, String type, Long beginDate, Long endDate) {
        var uri = UriBuilder.of(this.uri)
                .queryParam("device_id", deviceId)
                // for deployment test: TODO: remove this line
                .queryParam("device_id", deviceId)
                .queryParam("module_id", moduleId)
                .queryParam("type", type)
                .queryParam("date_begin", beginDate)
                .queryParam("date_end", endDate)
                .build();
        HttpRequest<?> req = HttpRequest.GET(uri)
                .header(HttpHeaders.USER_AGENT, "Micronaut HTTP Client")
                .header(HttpHeaders.ACCEPT, "application/json")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + configurationService.getNetatmoConfiguration().accessToken());
        return httpClient.retrieve(req, MeteoResponse.class);

    }
}
