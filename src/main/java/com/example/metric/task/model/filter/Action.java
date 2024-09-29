package com.example.metric.task.model.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Action {
    EQ("="),
    GT(">"),
    LT("<"),
    GTE(">="),
    LTE("<="),
    LIKE("like");

    private String value;

    @JsonCreator
    public static Action fromString(String value) {
        return Action.valueOf(value.toUpperCase());
    }


    @Override
    public String toString() {
        return value;
    }
}
