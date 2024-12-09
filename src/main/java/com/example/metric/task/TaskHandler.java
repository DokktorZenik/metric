package com.example.metric.task;


import com.example.metric.task.model.TaskRequest;
import com.example.metric.util.HandlerUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.example.metric.util.HandlerUtil.requestToMono;

@Component
public class TaskHandler {

    @Autowired
    private TaskService taskService;

    @SneakyThrows
    public Mono<ServerResponse> getMetric(ServerRequest req) {
        return requestToMono(req, TaskRequest.class)
                .flatMap(taskService::getMetric)
                .flatMap(HandlerUtil::singleResponse);
    }
}
