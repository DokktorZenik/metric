package com.example.metric.task;

import com.example.metric.task.model.filter.Action;
import com.example.metric.task.model.filter.DataType;
import com.example.metric.task.model.filter.FilterModel;
import com.example.metric.util.QueryBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskQueryBuilder {

    public String build(List<String> fields, List<FilterModel> filters, Long orgId, Long projectId) {
        filters.add(new FilterModel("org_id", Action.EQ, List.of(orgId.toString()), DataType.LONG));
        filters.add(new FilterModel("project_id", Action.EQ, List.of(projectId.toString()), DataType.LONG));
        return QueryBuilder
                .builder()
                .select(fields)
                .where(filters)
                .from("project_tasks")
                .desc(true)
                .orderBy(fields.get(0))
                .build()
                .buildQuery();
    }


}
