package com.meteo.adapter.api.netatmo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Serdeable
@Introspected
public class MeteoResponse {

    private final Map<Instant, List<Double>> body;
    private final String status;
    private final double timeExec;
    private final Instant timeServer;

    @JsonCreator
    public MeteoResponse(
            @JsonProperty("body") Map<String, List<Double>> body,
            @JsonProperty("status") String status,
            @JsonProperty("time_exec") double timeExec,
            @JsonProperty("time_server") long timeServer
    ) {
        this.status = status;
        this.timeExec = timeExec;
        this.timeServer = Instant.ofEpochSecond(timeServer);

        // Convert map keys (seconds) → Instant
        this.body = body.entrySet().stream()
                .collect(
                        java.util.stream.Collectors.toMap(
                                e -> Instant.ofEpochSecond(Long.parseLong(e.getKey())),
                                Map.Entry::getValue
                        )
                );
    }

    public Map<Instant, List<Double>> getBody() {
        return body;
    }

    public String getStatus() {
        return status;
    }

    public double getTimeExec() {
        return timeExec;
    }

    public Instant getTimeServer() {
        return timeServer;
    }

    @Override
    public String toString() {
        return "MeteoResponse{" +
                "body=" + body +
                ", status='" + status + '\'' +
                ", timeExec=" + timeExec +
                ", timeServer=" + timeServer +
                '}';
    }
}
