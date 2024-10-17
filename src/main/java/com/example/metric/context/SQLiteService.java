package com.example.metric.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SQLiteService {

    @Value("${sqlite.metadata-url}")
    private String METADATA_DB_URL;

    public Mono<Tuple2<Long, Long>> getIds(String orgName, String projectName) {
        try (Connection connection = DriverManager.getConnection(METADATA_DB_URL)) {
            Long orgId = getIdByField(connection, "organizations", "name", orgName);
            Long projectId = getIdByField(connection, "projects", "name", projectName);
            if (orgId == null || projectId == null) {
                return Mono.error(() -> new IllegalStateException("Could not find organizations and projects"));
            }
            return Mono.just(Tuples.of(orgId, projectId));
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
    }

    public Long getIdByField(Connection connection, String tableName, String fieldName, String fieldValue) throws SQLException {
        String query = String.format("SELECT id FROM %s WHERE %s = ?", tableName, fieldName);
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, fieldValue);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("id");
            }
        }
        return null;
    }
}
