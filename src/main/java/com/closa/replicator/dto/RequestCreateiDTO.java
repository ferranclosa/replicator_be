package com.closa.replicator.dto;


import com.closa.replicator.domain.enums.SupportedSystems;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RequestCreateiDTO {

    @NotNull
    @NotBlank
    @Length(max = 6, min = 3)
    private String requestCode;
    @NotBlank
    @NotNull
    @Length(max = 256)
    private String requestDescription;

    @NotNull
    private SupportedSystems sourceSystem;

    @NotBlank
    @NotNull
    private String sourceURL;

    @NotBlank
    @NotNull
    private String sourceUser;
    @NotBlank
    @NotNull
    private String sourceCred;
    private String sourceDriver;
    @NotBlank
    @NotNull
    private String sourceSchema;
    @NotBlank
    @NotNull
    private String sourceTempSchema;
    @NotNull
    private SupportedSystems targetSystem;
    @NotBlank
    @NotNull
    private String targetURL;
    @NotBlank
    @NotNull
    private String targetUser;
    @NotBlank
    @NotNull
    private String targetCred;
    @NotBlank
    @NotNull
    private String targetDriver;
    @NotBlank
    @NotNull
    private String targetSchema;
    @NotNull

    private Boolean targetDropBefore;
    @NotNull
    private Boolean tragetClearBefore;
    @NotNull
    @Min(0)
    private Integer batchSize;

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

    public SupportedSystems getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(SupportedSystems sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public SupportedSystems getTargetSystem() {
        return targetSystem;
    }

    public void setTargetSystem(SupportedSystems targetSystem) {
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
