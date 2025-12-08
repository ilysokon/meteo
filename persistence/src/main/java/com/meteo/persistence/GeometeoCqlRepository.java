package com.meteo.persistence;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.meteo.domain.model.Geometeo;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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
                                "VALUES (:device_id, :module_id, :type, :date_begin, :date_end, :scale, :optimize, :realtime, :measure)");
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
}
