package com.closa.replicator.domain;

import com.closa.replicator.domain.enums.JoinType;
import com.closa.replicator.domain.enums.LinkType;

import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "rp_many")
@DiscriminatorValue("MANY")
public class RPMethMANY extends RPExtraction {
    private String oneColumn;
    private String manyColumn;
    private String manyFile;
    @Enumerated(EnumType.STRING)
    private JoinType joinType;
    private LocalDateTime lastUpdated;
    private String extMethod = "MANY";

    public String getExtMethod() {
        return extMethod;
    }

    public void setOneColumn(String oneColumn) {
        this.oneColumn = oneColumn;
    }

    public void setManyColumn(String manyColumn) {
        this.manyColumn = manyColumn;
    }

    public void setManyFile(String manyFile) {
        this.manyFile = manyFile;
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public void setJoinType(JoinType joinType) {
        this.joinType = joinType;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setExtMethod(String extMethod) {
        this.extMethod = extMethod;
    }

    public String getManyFile() {
        return manyFile;
    }

    public RPMethMANY(String oneColumn, String manyFile, String manyColumn) {
        this.oneColumn = oneColumn;
        this.manyFile = manyFile;
        this.joinType = JoinType.INNER;
        this.manyColumn = manyColumn;
        this.lastUpdated = LocalDateTime.now();
    }

    public RPMethMANY(String oneColumn, String manyFile, String manyColumn, LinkType linkType, Integer ordering) {
        super(linkType, ordering);
        this.oneColumn = oneColumn;
        this.manyFile = manyFile;
        this.joinType = JoinType.INNER;
        this.manyColumn = manyColumn;
        this.lastUpdated = LocalDateTime.now();
    }

    public RPMethMANY(String oneColumn, String manyFile, String manyColumn, JoinType joinType, LinkType linkType, Integer ordering) {
        super(linkType, ordering);
        this.oneColumn = oneColumn;
        this.manyFile = manyFile;
        this.joinType = joinType;
        this.manyColumn = manyColumn;
        this.lastUpdated = LocalDateTime.now();
    }

    public RPMethMANY() {
    }

    public String getOneColumn() {
        return oneColumn;
    }

    public String getManyColumn() {
        return manyColumn;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
}
