package com.qaelum.dms.domain.entity.coach;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.UUID;

/**
 * Created by einha on 2/20/2018.
 */
@XStreamAlias("CoachProject")
public class CoachProject {

    //unique (?) name for the project
    private String projectName;
    private String projectKey;
    //QMENTUM, JCI etc...
    private String auditKey;
    private String language;

    private UUID projectUUID;
    private boolean trash = false;

    public CoachProject(String projectName, String projectKey, String auditKey, String language) {
        this.projectName = projectName;
        this.projectKey = projectKey;
        this.auditKey = auditKey;
        this.language = language;

        this.projectUUID = UUID.randomUUID();
    }

//    public String getProjectName() {
//        return projectName;
//    }
//
//    public void setProjectName(String projectName) {
//        this.projectName = projectName;
//    }
//
//    public String getProjectKey() {
//        return projectKey;
//    }
//
//    public void setProjectKey(String projectKey) {
//        this.projectKey = projectKey;
//    }
//
//    public String getAuditKey() {
//        return auditKey;
//    }
//
//    public void setAuditKey(String auditKey) {
//        this.auditKey = auditKey;
//    }

    public UUID getProjectUUID() {
        return projectUUID;
    }

    public void setProjectUUID(UUID projectUUID) {
        this.projectUUID = projectUUID;
    }
}
