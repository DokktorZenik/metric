package com.example.metric.task;

import com.example.metric.task.model.TaskRequest;
import com.example.metric.task.model.TasksResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Mono<TasksResponse> getMetric(TaskRequest taskRequest) {
        return taskRepository.getTasks(taskRequest)
                .collectList()
                .map(TasksResponse::new);
    }
}
