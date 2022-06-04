package com.closa.replicator.domain.views;

public class ConnectionParams {
    private String sourceSystem;
    private String sourceSchema;
    private String sourceUser;
    private String sourceCreds;
    private String sourceDriver;
    private String sourceURL;
    private String targetSystem;
    private String targetSchema;
    private String targetUser;
    private String targetCreds;
    private String targetDriver;
    private String targetURL;

    public String getSourceSchema() {
        return sourceSchema;
    }

    public void setSourceSchema(String sourceSchema) {
        this.sourceSchema = sourceSchema;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    public String getTargetURL() {
        return targetURL;
    }

    public void setTargetURL(String targetURL) {
        this.targetURL = targetURL;
    }

    public String getTargetSchema() {
        return targetSchema;
    }

    public void setTargetSchema(String targetSchema) {
        this.targetSchema = targetSchema;
    }

    public String getSourceDriver() {
        return sourceDriver;
    }

    public void setSourceDriver(String sourceDriver) {
        this.sourceDriver = sourceDriver;
    }

    public String getSourceUser() {
        return sourceUser;
    }

    public void setSourceUser(String sourceUser) {
        this.sourceUser = sourceUser;
    }

    public String getSourceCreds() {
        return sourceCreds;
    }

    public void setSourceCreds(String sourceCreds) {
        this.sourceCreds = sourceCreds;
    }

    public String getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }

    public String getTargetCreds() {
        return targetCreds;
    }

    public void setTargetCreds(String targetCreds) {
        this.targetCreds = targetCreds;
    }

    public String getTargetDriver() {
        return targetDriver;
    }

    public void setTargetDriver(String targetDriver) {
        this.targetDriver = targetDriver;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String getTargetSystem() {
        return targetSystem;
    }

    public void setTargetSystem(String targetSystem) {
        this.targetSystem = targetSystem;
    }
}
