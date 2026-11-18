package com.meteo.persistence;

import java.time.Instant;

public record NetatmoToken(Instant refreshedAt, String accessToken, String refreshToken, Integer expiresIn) implements Comparable<NetatmoToken>{

    @Override
    public String toString() {
        return "NetatmoToken{" +
                "refreshedAt=" + refreshedAt +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresAt=" + expiresIn + '\'' +
                '}';
    }

    @Override
    public int compareTo(NetatmoToken other) {
        return refreshedAt.compareTo(other.refreshedAt);
    }
}
