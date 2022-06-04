package com.closa.replicator.domain.enums;

import java.util.ArrayList;
import java.util.List;

public enum ExecutionCycle implements Comparable<ExecutionCycle> {

    CON(100, "CONNECTION TO SOURCE AND TARGET"),
    DRP( 200, "DROP ANY TARGET TABLES"),
    POP( 300, "POPULATE WORK SCHEMA WITH EXTRACTION RULES"),
    FIX(  400, "EXECUTE ANY POST POPULATE JOBS BEFORE TRANSFERT"),
    MSK( 450, "MASK ANY SENSITIVE DATA"),
    MET( 500, "GET METADATA FROM WORK AND BUILD TARGET"),
    CLR( 600, "CLEAR TARGET DATA"),
    CPY( 800, "COPY DATA CONTENTS");
        
        
        private String description; 
        private int naturalOrder;
        private List<ExecutionCycle> cyclesByOrder = new ArrayList<>();

    ExecutionCycle( int naturalOrder, String description) {
        this.description = description;
        this.naturalOrder = naturalOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNaturalOrder() {
        return naturalOrder;
    }

    public void setNaturalOrder(int naturalOrder) {
        this.naturalOrder = naturalOrder;
    }

}
