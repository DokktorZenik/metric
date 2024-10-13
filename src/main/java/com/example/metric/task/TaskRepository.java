package com.example.metric.task;

import com.example.metric.context.ProjectContext;
import com.example.metric.task.model.TaskRequest;
import com.example.metric.task.model.TaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

import static com.example.metric.context.ProjectContextHolder.getContext;


@Component
public class TaskRepository {

    @Autowired
    private TaskQueryBuilder taskQueryBuilder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Flux<TaskResponse> getTasks(TaskRequest taskRequest) {
        ProjectContext context = getContext();

        String sql = taskQueryBuilder.build(taskRequest.getFields(), taskRequest.getFilters(), context.orgId(), context.projectId());

        return Flux.defer(() -> Flux.fromIterable(queryTasks(sql, taskRequest.getFields())))
                .subscribeOn(Schedulers.boundedElastic());
    }

    private List<TaskResponse> queryTasks(String sql, List<String> fields) {
        TaskMapper taskMapper = new TaskMapper(fields);
        return jdbcTemplate.query(sql, taskMapper);
    }

}
