/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.closa.replicator.domain;

import java.util.Objects;

/**
 * @author A8515150
 */
public class WorkColumn {
    private String ColumnName;
    private String ColumnType;
    private int ColumnSQLDtaType;
    private String IsNullable;
    private int ColumnPrecision;
    private int ColumnScale;
    private String ColumnLabel;

    public WorkColumn() {
    }

    public String getColumnName() {
        return ColumnName;
    }

    public void setColumnName(String ColumnName) {
        this.ColumnName = ColumnName;
    }

    public String getColumnType() {
        return ColumnType;
    }

    public void setColumnType(String ColumnType) {
        this.ColumnType = ColumnType;
    }

    public int getColumnPrecision() {
        return ColumnPrecision;
    }

    public void setColumnPrecision(int ColumnPrecision) {
        this.ColumnPrecision = ColumnPrecision;
    }

    public int getColumnScale() {
        return ColumnScale;
    }

    public void setColumnScale(int ColumnScale) {
        this.ColumnScale = ColumnScale;
    }

    public String getColumnLabel() {
        return ColumnLabel;
    }

    public void setColumnLabel(String ColumnLabel) {
        this.ColumnLabel = ColumnLabel;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.ColumnName);
        hash = 53 * hash + Objects.hashCode(this.ColumnType);
        hash = 53 * hash + this.ColumnPrecision;
        hash = 53 * hash + this.ColumnScale;
        hash = 53 * hash + Objects.hashCode(this.ColumnLabel);
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
        final WorkColumn other = (WorkColumn) obj;
        return true;
    }

    public int getColumnSQLDtaType() {
        return ColumnSQLDtaType;
    }

    public void setColumnSQLDtaType(int ColumnSQLDtaType) {
        this.ColumnSQLDtaType = ColumnSQLDtaType;
    }

    public String getIsNullable() {
        return IsNullable;
    }

    public void setIsNullable(String IsNullable) {
        this.IsNullable = IsNullable;
    }
}
