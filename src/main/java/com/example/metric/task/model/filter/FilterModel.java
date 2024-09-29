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
        if (values == null || values.isEmpty()) {
            return "";
        }

        String conditions = values.stream()
                .map(elem -> String.format("%s %s %s", convertName(field), action, transformToLike(elem)))
                .collect(Collectors.joining(" or "));

        return values.size() > 1 ? wrap(conditions) : conditions;
    }

    private String wrap(String string) {
        return "(" + string + ")";
    }

    private String convertName(String name) {
        return dataType == DataType.STRING ? "LOWER(" + name + ")" : name;
    }

    private String convertData(String data) {
        return switch (dataType) {
            case STRING -> "LOWER('" + data + "')";
            case DATA -> "'" + data + "'";
            case LONG -> data;
            default -> data;
        };
    }

    private String transformToLike(String value) {
        return convertData(action == Action.LIKE ? "%" + value + "%" : value);
    }
}
