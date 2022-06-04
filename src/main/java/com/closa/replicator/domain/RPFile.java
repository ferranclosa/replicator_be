package com.closa.replicator.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "rp_file")
public class RPFile implements EntityCommon {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "FILE_ID")
    @TableGenerator(name = "FILE_ID", table = "GEN_ID",
            pkColumnName = "key_name", valueColumnName = "key_val",
            pkColumnValue = "FILE_ID", initialValue = 1, allocationSize = 5)
    private Long id;
    @Column(nullable = false)
    private String fileName;
    private Integer fileOrder;
    @JsonIgnore
    @ManyToOne
    private RPRequest request;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rpFile")
    private List<RPExtraction> extractionList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rpFile")
    private List<RPScript> scriptList = new ArrayList<>();

    public Integer getFileOrder() {
        return fileOrder;
    }

    public void setFileOrder(Integer fileOrder) {
        this.fileOrder = fileOrder;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public RPRequest getRequest() {
        return request;
    }

    public void setRequest(RPRequest request) {
        this.request = request;
    }

    public List<RPExtraction> getExtractionList() {
        return extractionList;
    }

    public void setExtractionList(List<RPExtraction> extractionList) {
        this.extractionList = extractionList;
    }

    public List<RPScript> getScriptList() {
        return scriptList;
    }

    public void addAScript(RPScript script){
        this.getScriptList().add(script);
        script.setRpFile(this);

    }
    public void setScriptList(List<RPScript> scriptList) {
        this.scriptList = scriptList;
    }

    public void addExtractionRule(RPExtraction rule) {
        this.extractionList.add(rule);
        rule.setRpFile(this);
    }

    public void removeExtractionRule(RPExtraction rule) {
        this.extractionList.remove(rule);
    }
}
