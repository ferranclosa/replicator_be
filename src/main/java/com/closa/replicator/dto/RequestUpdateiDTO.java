package com.closa.replicator.dto;


import com.sun.istack.NotNull;

public class RequestUpdateiDTO {

    @NotNull
    private String requestCode;
    private String requestDescription;

    private String sourceSystem;
    private String sourceURL;
    private String sourceUser;
    private String sourceCred;
    private String sourceDriver;
    private String sourceSchema;
    private String sourceTempSchema;

    private String targetSystem;
    private String targetURL;
    private String targetUser;
    private String targetCred;
    private String targetDriver;
    private String targetSchema;

    private Boolean targetDropBefore;
    private Boolean tragetClearBefore;
    private Integer batchSize;

    public RequestUpdateiDTO() {
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    public String getSourceUser() {
        return sourceUser;
    }

    public void setSourceUser(String sourceUser) {
        this.sourceUser = sourceUser;
    }

    public String getSourceCred() {
        return sourceCred;
    }

    public void setSourceCred(String sourceCred) {
        this.sourceCred = sourceCred;
    }

    public String getSourceDriver() {
        return sourceDriver;
    }

    public void setSourceDriver(String sourceDriver) {
        this.sourceDriver = sourceDriver;
    }

    public String getSourceSchema() {
        return sourceSchema;
    }

    public void setSourceSchema(String sourceSchema) {
        this.sourceSchema = sourceSchema;
    }

    public String getSourceTempSchema() {
        return sourceTempSchema;
    }

    public void setSourceTempSchema(String sourceTempSchema) {
        this.sourceTempSchema = sourceTempSchema;
    }

    public String getTargetSystem() {
        return targetSystem;
    }

    public void setTargetSystem(String targetSystem) {
        this.targetSystem = targetSystem;
    }

    public String getTargetURL() {
        return targetURL;
    }

    public void setTargetURL(String targetURL) {
        this.targetURL = targetURL;
    }

    public String getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }

    public String getTargetCred() {
        return targetCred;
    }

    public void setTargetCred(String targetCred) {
        this.targetCred = targetCred;
    }

    public String getTargetDriver() {
        return targetDriver;
    }

    public void setTargetDriver(String targetDriver) {
        this.targetDriver = targetDriver;
    }

    public String getTargetSchema() {
        return targetSchema;
    }

    public void setTargetSchema(String targetSchema) {
        this.targetSchema = targetSchema;
    }

    public Boolean getTargetDropBefore() {
        return targetDropBefore;
    }

    public void setTargetDropBefore(Boolean targetDropBefore) {
        this.targetDropBefore = targetDropBefore;
    }

    public Boolean getTragetClearBefore() {
        return tragetClearBefore;
    }

    public void setTragetClearBefore(Boolean tragetClearBefore) {
        this.tragetClearBefore = tragetClearBefore;
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }
}
