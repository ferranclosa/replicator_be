package com.closa.replicator.domain;

import com.closa.replicator.domain.enums.LinkType;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "rp_where")
@DiscriminatorValue("WHERE")
public class RPMethWHERE extends RPExtraction {
    @Column(nullable = false)
    private String whereStatement;
    private String extMethod = "WHERE";

    public String getExtMethod() {
        return extMethod;
    }

    public void setExtMethod(String extMethod) {
        this.extMethod = extMethod;
    }

    private LocalDateTime lastUpdated;

    public RPMethWHERE(String whereStatement) {
        super(LinkType.No_LINK, 0);
        this.whereStatement = whereStatement;
        this.setLastUpdated();
    }

    public RPMethWHERE(String whereStatement, LinkType linkType, Integer ordering) {
        super(linkType, ordering);
        this.whereStatement = whereStatement;
        this.setLastUpdated();
    }

    public RPMethWHERE() {
    }

    public String getWhereStatement() {
        return whereStatement;
    }

    public void setWhereStatement(String whereStatement) {
        this.whereStatement = whereStatement;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated() {
        this.lastUpdated = LocalDateTime.now();
    }
}
