package com.closa.replicator.domain;

public class WorkTableDetails {
    private String tdTableSchema;
    private String tdTableName;
    private String tdTableComment;
    private Integer tdTableColumns;
    private Integer tdTableIndexes;
    private String tdTablePrimaryKey;
    private String tdColumnName;
    private String tdColumnComment;
    private String tdColumnType;
    private Integer tdColumnLength;
    private Integer tdColumnPrecision;
    private Integer tdColumnIndex;
    private Boolean tdColumnNullable;
    private String tdColumnDefault;
    private String tdColumnSampleValue;

    public WorkTableDetails() {
    }

    public String getTdTableSchema() {
        return tdTableSchema;
    }

    public void setTdTableSchema(String tdTableSchema) {
        this.tdTableSchema = tdTableSchema;
    }

    public String getTdTableName() {
        return tdTableName;
    }

    public void setTdTableName(String tdTableName) {
        this.tdTableName = tdTableName;
    }

    public String getTdTableComment() {
        return tdTableComment;
    }

    public void setTdTableComment(String tdTableComment) {
        this.tdTableComment = tdTableComment;
    }

    public Integer getTdTableColumns() {
        return tdTableColumns;
    }

    public void setTdTableColumns(Integer tdTableColumns) {
        this.tdTableColumns = tdTableColumns;
    }

    public Integer getTdTableIndexes() {
        return tdTableIndexes;
    }

    public void setTdTableIndexes(Integer tdTableIndexes) {
        this.tdTableIndexes = tdTableIndexes;
    }

    public String getTdTablePrimaryKey() {
        return tdTablePrimaryKey;
    }

    public void setTdTablePrimaryKey(String tdTablePrimaryKey) {
        this.tdTablePrimaryKey = tdTablePrimaryKey;
    }

    public String getTdColumnName() {
        return tdColumnName;
    }

    public void setTdColumnName(String tdColumnName) {
        this.tdColumnName = tdColumnName;
    }

    public String getTdColumnComment() {
        return tdColumnComment;
    }

    public void setTdColumnComment(String tdColumnComment) {
        this.tdColumnComment = tdColumnComment;
    }

    public String getTdColumnType() {
        return tdColumnType;
    }

    public void setTdColumnType(String tdColumnType) {
        this.tdColumnType = tdColumnType;
    }

    public Integer getTdColumnLength() {
        return tdColumnLength;
    }

    public void setTdColumnLength(Integer tdColumnLength) {
        this.tdColumnLength = tdColumnLength;
    }

    public Integer getTdColumnPrecision() {
        return tdColumnPrecision;
    }

    public void setTdColumnPrecision(Integer tdColumnPrecision) {
        this.tdColumnPrecision = tdColumnPrecision;
    }

    public Integer getTdColumnIndex() {
        return tdColumnIndex;
    }

    public void setTdColumnIndex(Integer tdColumnIndex) {
        this.tdColumnIndex = tdColumnIndex;
    }

    public Boolean getTdColumnNullable() {
        return tdColumnNullable;
    }

    public void setTdColumnNullable(Boolean tdColumnNullable) {
        this.tdColumnNullable = tdColumnNullable;
    }

    public String getTdColumnDefault() {
        return tdColumnDefault;
    }

    public void setTdColumnDefault(String tdColumnDefault) {
        this.tdColumnDefault = tdColumnDefault;
    }

    public String getTdColumnSampleValue() {
        return tdColumnSampleValue;
    }

    public void setTdColumnSampleValue(String tdColumnSampleValue) {
        this.tdColumnSampleValue = tdColumnSampleValue;
    }
}
