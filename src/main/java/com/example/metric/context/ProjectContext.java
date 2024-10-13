package com.example.metric.context;

import lombok.Getter;

@Getter
public record ProjectContext(Long orgId, Long projectId) {
}
