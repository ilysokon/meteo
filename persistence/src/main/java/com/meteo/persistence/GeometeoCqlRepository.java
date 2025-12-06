package com.meteo.persistence;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.meteo.domain.model.Geometeo;
import com.simba.cassandra.shaded.datastax.driver.core.utils.UUIDs;
import jakarta.inject.Singleton;

import java.sql.Timestamp;
import java.util.UUID;

// @Singleton
public class GeometeoCqlRepository {
    private final PreparedStatement insertGeometeoPS;
    private final CqlSession session;
    private final String keyspace;

    public GeometeoCqlRepository(final CqlSession session, final String keyspace) {
        this.session = session;
        this.keyspace = keyspace;

        insertGeometeoPS =
                session.prepare(
                        "INSERT INTO meteo.geometeo (device_id, module_id, type, date_begin, date_end, scale, optimize, realtime, measure) " +
                                "VALUES (:device_id, :module_id, :type, :date_begin, :date_end, :scale, :optimize, :realtime, :measure) )");
    }

    public UUID insertGeometeo(Geometeo geometeo, UUID timeUUID) {
        final var timestamp = new Timestamp(UUIDs.unixTimestamp(timeUUID));
        final var localDateTime = timestamp.toLocalDateTime();
        final var localDate = localDateTime.toLocalDate();

        var bs = insertGeometeoPS.boundStatementBuilder()
                .setString("device_id", geometeo.getDeviceId())
                .setString("module_id", geometeo.getModuleId())
//                .setInstant("date_begin", timestamp.toInstant())
                .setLong("date_begin", geometeo.getBeginDate())
//                .setInstant("date_end", timestamp.toInstant())
                .setLong("date_end", geometeo.getEndDate())
                .setString("cale", "30min")
                .setBoolean("optimize", false)
                .setBoolean("realtime", true)
                .setMap("measure", geometeo.getMeasure(), Long.class, Double.class)
                .build();

        session.execute(bs);

        return timeUUID;
    }
}
