package com.example.metric.util;

import com.example.metric.task.model.filter.FilterModel;
import lombok.Builder;

import java.util.List;

@Builder
public class QueryBuilder {

    public List<String> select;

    public String from;

    public List<FilterModel> where;

    public String orderBy;

    public Boolean desc;

    public String buildQuery() {
        return "select "
                + (select.isEmpty() ? "*" : select())
                + (from == null ? "from test" : "from %s ".formatted(from))
                + (where.isEmpty() ? "" : where())
                + (orderBy != null && !orderBy.isEmpty() ? "order by %s ".formatted(orderBy) : "")
                + (desc ? "desc ": "acs");
    }


    private String select() {
        return String.join(",", select) + " ";
    }

    private String where() {
        String result = "where ";
        for (FilterModel filter : where) {
            result += filter.toString() + " and ";
        }
        return result + " ";
    }
}
