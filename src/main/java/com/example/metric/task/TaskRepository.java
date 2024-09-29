package com.example.metric.task;

import com.example.metric.task.model.TaskRequest;
import com.example.metric.task.model.TaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
public class TaskRepository {

    @Autowired
    private TaskQueryBuilder taskQueryBuilder;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public Flux<TaskResponse> getTasks(TaskRequest taskRequest) {

        String build = taskQueryBuilder.build(taskRequest.getFields(), taskRequest.getFilters(), 1L, 1L);

        TaskMapper taskMapper = new TaskMapper(taskRequest.getFields());

        List<TaskResponse> responses = jdbcTemplate.query(build, taskMapper);

        return Flux.fromIterable(responses);
    }

}
