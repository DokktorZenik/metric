package com.example.metric.task;

import com.example.metric.task.model.FieldResponse;
import com.example.metric.task.model.TaskResponse;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class TaskMapper implements RowMapper<TaskResponse> {

    private List<String> fields;

    @Override
    public TaskResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        TaskResponse taskResponse = new TaskResponse();
        List<FieldResponse> fieldResponses = new ArrayList<>();
        while (rs.next()) {
            FieldResponse fieldResponse = new FieldResponse();
            for (String field : fields) {
                fieldResponse.setFieldName(field);
                fieldResponse.setFieldValue(rs.getObject(field));
            }
            fieldResponses.add(fieldResponse);
        }
        taskResponse.setFields(fieldResponses);
        return taskResponse;
    }
}
