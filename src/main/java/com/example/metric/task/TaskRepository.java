package com.example.metric.task;

import com.example.metric.task.model.TaskRequest;
import com.example.metric.task.model.TaskResponse;
import io.r2dbc.spi.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;


@Component
public class TaskRepository {

    @Autowired
    private TaskQueryBuilder taskQueryBuilder;

    @Autowired
    private DatabaseClient databaseClient;

    public Flux<TaskResponse> getTasks(TaskRequest taskRequest) {

        String build = taskQueryBuilder.build(taskRequest.getFields(), taskRequest.getFilters(), 1L, 1L);

        TaskMapper taskMapper = new TaskMapper(taskRequest.getFields());

        return databaseClient.sql(build)
                .map(raw -> taskMapper.mapRow((Row) raw))
                .all();
    }

}
