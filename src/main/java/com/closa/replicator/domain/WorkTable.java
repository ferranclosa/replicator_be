/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.closa.replicator.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * @author A8515150
 */
public class WorkTable implements Serializable {
    private String TableName;
    private String TableSchema;
    private String TableType;
    private String TableLabels;
    private Collection<WorkColumn> tableWorkColumns;
    private Collection<WorkIndex> tableWorkIndices;

    public WorkTable() {
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String TableName) {
        this.TableName = TableName;
    }

    public String getTableSchema() {
        return TableSchema;
    }

    public void setTableSchema(String TableSchema) {
        this.TableSchema = TableSchema;
    }

    public String getTableLabels() {
        return TableLabels;
    }

    public void setTableLabels(String TableLabels) {
        this.TableLabels = TableLabels;
    }

    public String getTableType() {
        return TableType;
    }

    public void setTableType(String TableType) {
        this.TableType = TableType;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WorkTable other = (WorkTable) obj;
        if (!Objects.equals(this.TableName, other.TableName)) {
            return false;
        }
        if (!Objects.equals(this.TableSchema, other.TableSchema)) {
            return false;
        }
        if (!Objects.equals(this.TableLabels, other.TableLabels)) {
            return false;
        }
        return true;
    }

    public Collection<WorkColumn> getTableWorkColumns() {
        return tableWorkColumns;
    }

    public void setTableWorkColumns(Collection<WorkColumn> tableWorkColumns) {
        this.tableWorkColumns = tableWorkColumns;
    }

    public Collection<WorkIndex> getTableWorkIndices() {
        return tableWorkIndices;
    }

    public void setTableWorkIndices(Collection<WorkIndex> tableWorkIndices) {
        this.tableWorkIndices = tableWorkIndices;
    }
}
