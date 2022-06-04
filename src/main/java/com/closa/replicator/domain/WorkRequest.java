/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.closa.replicator.domain;

import com.closa.replicator.functions.James;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

/**
 * @author A8515150
 */
public class WorkRequest implements Serializable {
    private long RequestID;
    private GregorianCalendar todayStart;
    private GregorianCalendar todayEnd;
    private String sqlStmt;
    private ArrayList<String> sqlStmts;
    private List<James.ColDesc> colDescs;
    private List<James.TabDesc> tabDescs;
    private RPRequest js;
    private WorkTable requestWorkTable;

    public GregorianCalendar getTodayStart() {
        return todayStart;
    }

    public WorkRequest(RPRequest js, WorkTable requestWorkTable) {
        this.js = js;
        this.requestWorkTable = requestWorkTable;
    }

    public WorkRequest() {
    }

    public RPRequest getJs() {
        return js;
    }

    public void setJs(RPRequest js) {
        this.js = js;
    }

    public WorkTable getRequestWorkTable() {
        return requestWorkTable;
    }

    public void setRequestWorkTable(WorkTable requestWorkTable) {
        this.requestWorkTable = requestWorkTable;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.js);
        hash = 41 * hash + Objects.hashCode(this.requestWorkTable);
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
        final WorkRequest other = (WorkRequest) obj;
        if (!Objects.equals(this.js, other.js)) {
            return false;
        }
        if (!Objects.equals(this.requestWorkTable, other.requestWorkTable)) {
            return false;
        }
        return true;
    }

    public void setTodayStart(GregorianCalendar todayStart) {
        this.todayStart = todayStart;
    }

    public GregorianCalendar getTodayEnd() {
        return todayEnd;
    }

    public void setTodayEnd(GregorianCalendar todayEnd) {
        this.todayEnd = todayEnd;
    }

    public String getSqlStmt() {
        return sqlStmt;
    }

    public void setSqlStmt(String sqlStmt) {
        this.sqlStmt = sqlStmt;
    }

    public ArrayList<String> getSqlStmts() {
        return sqlStmts;
    }

    public void setSqlStmts(ArrayList<String> sqlStmts) {
        this.sqlStmts = sqlStmts;
    }

    public List<James.ColDesc> getColDescs() {
        return colDescs;
    }

    public void setColDescs(List<James.ColDesc> colDescs) {
        this.colDescs = colDescs;
    }

    public List<James.TabDesc> getTabDescs() {
        return tabDescs;
    }

    public void setTabDescs(List<James.TabDesc> tabDescs) {
        this.tabDescs = tabDescs;
    }
}
