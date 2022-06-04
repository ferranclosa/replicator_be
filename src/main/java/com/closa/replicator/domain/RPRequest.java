package com.closa.replicator.domain;

import com.closa.replicator.domain.views.ConnectionParams;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rp_request")
public class RPRequest implements EntityCommon {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "REQU_ID")
    @TableGenerator(name = "REQU_ID", table = "GEN_ID",
            pkColumnName = "key_name", valueColumnName = "key_val",
            pkColumnValue = "REQU_ID", initialValue = 1, allocationSize = 5)
    private Long id;
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
    private Boolean targetClearBefore;
    private Integer batchSize;
    @OneToMany(mappedBy = "request",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<RPFile> fileList = new ArrayList<>();

    public RPRequest() {
    }


    public String getSourceTempSchema() {
        return sourceTempSchema;
    }

    public void setSourceTempSchema(String sourceTempSchema) {
        this.sourceTempSchema = sourceTempSchema;
    }

    public Boolean getTargetClearBefore() {
        return targetClearBefore;
    }

    public void setTargetClearBefore(Boolean targetClearBefore) {
        this.targetClearBefore = targetClearBefore;
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

    public Boolean getTargetDropBefore() {
        return targetDropBefore;
    }

    public void setTargetDropBefore(Boolean targetDropBefore) {
        this.targetDropBefore = targetDropBefore;
    }

    public List<RPFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<RPFile> fileList) {
        this.fileList = fileList;
    }

    public String getSourceSchema() {
        return sourceSchema;
    }

    public void setSourceSchema(String sourceSchema) {
        this.sourceSchema = sourceSchema;
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

    public String getTargetDriver() {
        return targetDriver;
    }

    public void setTargetDriver(String targetDriver) {
        this.targetDriver = targetDriver;
    }

    public String getRequestCode() {
        return requestCode;
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

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }
}
