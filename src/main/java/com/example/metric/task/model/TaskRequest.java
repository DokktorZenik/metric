package com.example.metric.task.model;

import com.example.metric.task.model.filter.FilterModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {

    List<String> fields;

    List<FilterModel> filters;
}
