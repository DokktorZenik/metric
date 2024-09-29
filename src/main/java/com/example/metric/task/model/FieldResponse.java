package com.example.metric.task.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldResponse {

    private String fieldName;

    private Object fieldValue;
}
