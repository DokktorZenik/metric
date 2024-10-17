package com.example.metric.task;

import com.example.metric.task.model.FieldResponse;
import com.example.metric.task.model.TaskResponse;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class TaskMapper  implements RowMapper<TaskResponse> {

    private final List<String> fields;

    private FieldResponse mapField(ResultSet resultSet, String fieldName) {
        FieldResponse fieldResponse = new FieldResponse();
        fieldResponse.setFieldName(fieldName);
        try {
            fieldResponse.setFieldValue(resultSet.getObject(fieldName));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fieldResponse;
    }

    @Override
    public TaskResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TaskResponse(fields.stream()
                .map(fieldName -> mapField(rs, fieldName))
                .collect(Collectors.toList()));
    }
}
