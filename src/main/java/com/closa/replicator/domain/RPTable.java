/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.closa.replicator.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author A8515150
 */

@Entity
@Table(name = "rp_table")
public class RPTable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_ID")
    @TableGenerator(name = "TABLE_ID", table = "GEN_ID",
            pkColumnName = "key_name", valueColumnName = "key_val",
            pkColumnValue = "TABLE_ID", initialValue = 1, allocationSize = 5)

    private Long id;

    private String tableName;
    private String tableSchema;
    private String tableType;
    private String tableLabels;

    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RPColumn> tableColumns;

    public RPTable() {
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getTableLabels() {
        return tableLabels;
    }

    public void setTableLabels(String tableLabels) {
        this.tableLabels = tableLabels;
    }

    public List<RPColumn> getTableColumns() {
        return tableColumns;
    }

    public void setTableColumns(List<RPColumn> tableColumns) {
        this.tableColumns = tableColumns;
    }


}
