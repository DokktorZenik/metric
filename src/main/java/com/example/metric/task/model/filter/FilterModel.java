package com.example.metric.task.model.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterModel {

    private String field;

    private Action action;

    private List<String> values;

    private DataType dataType;

    @Override
    public String toString() {
        String collect = values.stream().map(elem -> field + " " + action + " " + transformToLike(elem)).collect(Collectors.joining(" or "));
        return values.size() > 1 ? this.wrap(collect) : collect;
    }

    private String wrap(String string) {
        return "(" + string + ")";
    }

    private String convertData(String data, DataType dataType) {
        switch (dataType) {
            case STRING:
            case DATA:
                return "'" + data + "'";
            case LONG:
                return data;
            default:
                return data;
        }
    }

    private String transformToLike(String value) {
        return convertData(action.equals(Action.LIKE) ? "%" + value + "%" : value, dataType);
    }
}
