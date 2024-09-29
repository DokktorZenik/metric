package com.example.metric.task;

import com.example.metric.task.model.FieldResponse;
import com.example.metric.task.model.TaskResponse;
import io.r2dbc.spi.Row;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class TaskMapper {

    private final List<String> fields;

    private FieldResponse mapField(Row row, String fieldName) {
        FieldResponse fieldResponse = new FieldResponse();
        fieldResponse.setFieldName(fieldName);
        fieldResponse.setFieldValue(row.get(fieldName));
        return fieldResponse;
    }

    public TaskResponse mapRow(Row row) {
        return new TaskResponse(fields.stream()
                .map(fieldName -> mapField(row, fieldName))
                .collect(Collectors.toList()));
    }
}
