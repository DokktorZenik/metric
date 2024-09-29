package com.example.metric.routes;

import com.example.metric.task.TaskHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TaskRoutes {

    @Autowired
    private TaskHandler taskHandler;

    @Bean
    RouterFunction<ServerResponse> taskApi() {
        RouterFunction<ServerResponse> taskRoute = route(POST("/metric"), taskHandler::getMetric);

        return nest(path("/v1/{orgName}/projects/{projectName}"), taskRoute);
    }
}
