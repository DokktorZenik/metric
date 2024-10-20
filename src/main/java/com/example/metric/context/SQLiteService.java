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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class SQLiteService {

    @Value("${sqlite.metadata-url}")
    private String METADATA_DB_URL;

    public Mono<Tuple2<Long, Long>> getIds(String orgName, String projectName) {
        try (Connection connection = DriverManager.getConnection(METADATA_DB_URL)) {
            Long orgId = getIdByField(connection, "organization", Map.of("name", orgName));
            Long projectId = getIdByField(connection, "project", Map.of("name", projectName, "org_id", orgId));
            if (orgId == null || projectId == null) {
                return Mono.error(() -> new IllegalStateException("Could not find organizations and projects"));
            }
            return Mono.just(Tuples.of(orgId, projectId));
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
    }

    public Long getIdByField(Connection connection, String tableName, Map<String, Object> conditions) throws SQLException {
        Set<Map.Entry<String, Object>> entries = conditions.entrySet();
        List<String> conditionsList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : entries) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();
            conditionsList.add(fieldName + "=" + value);
        }

        String query = String.format("SELECT id FROM %s WHERE %s", tableName, String.join(" AND ", conditionsList));
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("id");
            }
        }
        return null;
    }
}
