package com.closa.replicator.domain;

import com.closa.replicator.domain.enums.LinkType;

import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "rp_all")
@DiscriminatorValue("ALL")
public class RPMethALL extends RPExtraction {
    private LocalDateTime lastUpdated;
    private String extMethod = "ALL";

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getExtMethod() {
        return extMethod;
    }

    public void setExtMethod(String extMethod) {
        this.extMethod = extMethod;
    }

    public RPMethALL() {
        super();
        super.setLinkType(LinkType.No_LINK);
        super.setOrdering(0);
        this.setLastUpdated();
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated() {
        this.lastUpdated = LocalDateTime.now();
    }
}
