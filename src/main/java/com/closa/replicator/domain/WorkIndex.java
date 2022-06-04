/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.closa.replicator.domain;

import java.io.Serializable;

/**
 * @author A8515150
 */
public class WorkIndex implements Serializable {
    private Boolean nonUnique;
    private String indexName;
    private short indexType;
    private short ordinalPos;
    private String columnName;
    ;
    private String descASC;
    private String filterCondition;

    public WorkIndex() {
    }

    public Boolean getNonUnique() {
        return nonUnique;
    }

    public void setNonUnique(Boolean nonUnique) {
        this.nonUnique = nonUnique;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public short getIndexType() {
        return indexType;
    }

    public void setIndexType(short indexType) {
        this.indexType = indexType;
    }

    public short getOrdinalPos() {
        return ordinalPos;
    }

    public void setOrdinalPos(short ordinalPos) {
        this.ordinalPos = ordinalPos;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDescASC() {
        return descASC;
    }

    public void setDescASC(String descASC) {
        this.descASC = descASC;
    }

    public String getFilterCondition() {
        return filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }
}
