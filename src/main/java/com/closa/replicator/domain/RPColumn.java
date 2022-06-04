/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.closa.replicator.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author A8515150
 */

@Entity
@Table(name= "rp_column")
public class RPColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "COLU_ID")
    @TableGenerator(name = "COLU_ID" , table = "GEN_ID",
            pkColumnName = "key_name", valueColumnName = "key_val",
            pkColumnValue = "COLU_ID", initialValue = 1, allocationSize = 5)

    private Long id;

    @ManyToOne
    @JsonIgnore
    private RPTable table ;

    private String columnName;
    private String columnType;
    private int columnSQLDtaType;
    private String IsNullable;
    private int columnPrecision;
    private int columnScale;
    private String columnLabel;

    public RPColumn() {
    }

    public RPTable getTable() {
        return table;
    }

    public void setTable(RPTable table) {
        this.table = table;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public int getColumnSQLDtaType() {
        return columnSQLDtaType;
    }

    public void setColumnSQLDtaType(int columnSQLDtaType) {
        this.columnSQLDtaType = columnSQLDtaType;
    }

    public int getColumnPrecision() {
        return columnPrecision;
    }

    public void setColumnPrecision(int columnPrecision) {
        this.columnPrecision = columnPrecision;
    }

    public int getColumnScale() {
        return columnScale;
    }

    public void setColumnScale(int columnScale) {
        this.columnScale = columnScale;
    }

    public String getColumnLabel() {
        return columnLabel;
    }

    public void setColumnLabel(String columnLabel) {
        this.columnLabel = columnLabel;
    }

    public int getcolumnPrecision() {
        return columnPrecision;
    }

    public void setcolumnPrecision(int columnPrecision) {
        this.columnPrecision = columnPrecision;
    }

    public int getcolumnScale() {
        return columnScale;
    }

    public void setcolumnScale(int columnScale) {
        this.columnScale = columnScale;
    }

    public String getcolumnLabel() {
        return columnLabel;
    }

    public void setcolumnLabel(String columnLabel) {
        this.columnLabel = columnLabel;
    }


    public String getIsNullable() {
        return IsNullable;
    }

    public void setIsNullable(String IsNullable) {
        this.IsNullable = IsNullable;
    }
}
