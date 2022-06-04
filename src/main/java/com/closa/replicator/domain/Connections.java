package com.closa.replicator.domain;

import java.sql.Connection;

public class Connections implements EntityCommon {
    private Connection conFrom;
    private Connection conTo;

    public Connections(Connection conFrom, Connection conTo) {
        this.conFrom = conFrom;
        this.conTo = conTo;
    }

    public Connection getConFrom() {
        return conFrom;
    }

    public Connection getConTo() {
        return conTo;
    }

    public void setConFrom(Connection conFrom) {
        this.conFrom = conFrom;
    }

    public void setConTo(Connection conTo) {
        this.conTo = conTo;
    }
}
