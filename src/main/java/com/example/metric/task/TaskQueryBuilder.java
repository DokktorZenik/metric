package com.example.metric.task;

import com.example.metric.task.model.filter.FilterModel;
import com.example.metric.util.QueryBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskQueryBuilder {

    public String build(List<String> fields, List<FilterModel> filters, Long orgId, Long projectId) {
        return QueryBuilder
                .builder()
                .select(fields)
                .where(filters)
                .from("test")
                .desc(true)
                .orderBy(fields.get(2))
                .build()
                .buildQuery();
    }


}
