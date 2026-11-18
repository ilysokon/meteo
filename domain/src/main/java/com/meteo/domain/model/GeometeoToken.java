package com.meteo.domain.model;

import java.time.Instant;

public record GeometeoToken(Instant refreshedAt, String accessToken, String refreshToken, Integer expiresIn) {
    @Override
    public String toString() {
        return "GeometeoToken{" +
                "refreshedAt=" + refreshedAt +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresAt=" + expiresIn + '\'' +
                '}';
    }
}
