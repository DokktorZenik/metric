package com.example.metric.context;

public class ProjectContext {
    private final Long orgId;
    private final Long projectId;


    public ProjectContext(Long orgId, Long projectId) {
        this.orgId = orgId;
        this.projectId = projectId;
    }


    public Long getOrgId() {
        return orgId;
    }

    public Long getProjectId() {
        return projectId;
    }
}
