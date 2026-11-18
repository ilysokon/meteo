package com.meteo.persistence;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.meteo.domain.model.Geometeo;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

// @Singleton
public class GeometeoCqlRepository {
    private final PreparedStatement insertGeometeoPS;
    private final PreparedStatement selectNetatmoTokenPS;
    private final CqlSession session;
    private final String keyspace;
    private final String accessToken;

    public GeometeoCqlRepository(final CqlSession session, final String keyspace, final String accessToken) {
        this.session = session;
        this.keyspace = keyspace;
        this.accessToken = accessToken;

        insertGeometeoPS =
                session.prepare(
                        "INSERT INTO meteo.geometeo (device_id, module_id, type, date_begin, date_end, scale, optimize, realtime, measure) " +
                                "VALUES (:device_id, :module_id, :type, :date_begin, :date_end, :scale, :optimize, :realtime, :measure)");

        selectNetatmoTokenPS =
                // select tokens from netatmo_token table from the spcified day
                /*
                 * date date,
                                  refreshed_at timestamp,
                                  expires_in int,
                                  access_token varchar,
                                  refresh_token varchar,
                 */
                session.prepare(
                        "select date, refreshed_at, expires_in, access_token, refresh_token from meteo.netatmo_token where date >= :date allow filtering"
                );
    }

    public UUID insertGeometeo(final Geometeo geometeo, final UUID timeUUID) {
        var bs = insertGeometeoPS.boundStatementBuilder()
                .setString("device_id", geometeo.getDeviceId())
                .setString("module_id", geometeo.getModuleId())
                .setString("type", geometeo.getType())
                .setInstant("date_begin", geometeo.getBeginDate())
//                .setLong("date_begin", geometeo.getBeginDate())
                .setInstant("date_end", geometeo.getEndDate())
//                .setLong("date_end", geometeo.getEndDate())
                .setString("scale", "30min")
                .setBoolean("optimize", false)
                .setBoolean("realtime", true)
                .setMap("measure", geometeo.getMeasure(), Instant.class, Double.class)
                .build();

        session.execute(bs);

        return timeUUID;
    }

    public NetatmoToken findLatestNetatmoToken() {
        var bs = selectNetatmoTokenPS.boundStatementBuilder()
                .setLocalDate("date", LocalDateTime.now().minusDays(1).toLocalDate())
                .build();

        final var resultSet = session.execute(bs);

        final List<Row> rows = resultSet.all();
        final List<NetatmoToken> netatmoTokens = new ArrayList<>();
        for (var row : rows) {
            netatmoTokens.add(new NetatmoToken(row.getInstant("refreshed_at"),
                                               row.getString("access_token"),
                                               row.getString("refresh_token"),
                                               row.getInt("expires_in")));
        }

        Collections.sort(netatmoTokens);

        return netatmoTokens.getFirst();
    }
}
