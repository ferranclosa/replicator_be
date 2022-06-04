package com.closa.replicator.domain;

import com.closa.replicator.domain.enums.LinkType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "rp_limit")
@DiscriminatorValue("LIMIT")
public class RPMethLIMIT extends RPExtraction {
    private Integer limitBy;
    private String extMethod = "LIMIT";
    private LocalDateTime lastUpdated;

    public String getExtMethod() {
        return extMethod;
    }

    public void setExtMethod(String extMethod) {
        this.extMethod = extMethod;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public RPMethLIMIT(Integer limitBy) {
        super(LinkType.No_LINK, 0);
        this.limitBy = limitBy;
        this.lastUpdated = LocalDateTime.now();
    }

    public RPMethLIMIT(Integer limitBy, LinkType linkType, Integer ordering) {
        super(linkType, ordering);
        this.limitBy = limitBy;
        this.lastUpdated = LocalDateTime.now();
    }

    public RPMethLIMIT() {
    }

    public Integer getLimitBy() {
        return limitBy;
    }

    public void setLimitBy(Integer limitBy) {
        this.limitBy = limitBy;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated() {
        this.lastUpdated = LocalDateTime.now();
    }
}
