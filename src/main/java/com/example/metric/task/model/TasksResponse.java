package com.example.metric.task.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class TasksResponse {

    @JsonProperty("_embedded")
    private Embedded embedded;

    @Value
    private static class Embedded {
        List<TaskResponse> tasks;
    }

    public TasksResponse(List<TaskResponse> tasks) {
        this.embedded = new Embedded(tasks);
    }

}
