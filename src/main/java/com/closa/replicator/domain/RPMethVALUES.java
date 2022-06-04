package com.closa.replicator.domain;

import com.closa.replicator.domain.enums.ColType;
import com.closa.replicator.domain.enums.LinkType;
import com.closa.replicator.domain.enums.RelationOperator;

import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "rp_values")
@DiscriminatorValue("VALUES")
public class RPMethVALUES extends RPExtraction {
    private String columnName;
    private String extMethod = "VALUES";

    public String getExtMethod() {
        return extMethod;
    }

    public void setExtMethod(String extMethod) {
        this.extMethod = extMethod;
    }

    public RPMethVALUES() {
    }

    @Enumerated(EnumType.STRING)
    private RelationOperator relationship;
    @Enumerated(EnumType.STRING)
    private ColType columnType;
    private String columnValue;
    private LocalDateTime lastUpdated;

    public RPMethVALUES(
            String columnName,
            String columnValue,
            RelationOperator relationship,
            ColType columnType,
            LinkType linkType,
            Integer ordering) {
        super(linkType, ordering);
        this.columnName = columnName;
        this.relationship = relationship;
        this.columnType = columnType;
        this.columnValue = columnValue;
        this.lastUpdated = LocalDateTime.now();
    }

    public RPMethVALUES(
            String columnName,
            String columnValue,
            RelationOperator relationship,
            ColType columnType,
            Integer ordering) {
        super(LinkType.No_LINK, ordering);
        this.columnName = columnName;
        this.relationship = relationship;
        this.columnType = columnType;
        this.columnValue = columnValue;
        this.lastUpdated = LocalDateTime.now();
    }

    public RPMethVALUES(
            String columnName,
            String columnValue,
            ColType columnType,
            Integer ordering) {
        super(LinkType.No_LINK, ordering);
        this.columnName = columnName;
        this.relationship = RelationOperator.EQUAL_TO;
        this.columnType = columnType;
        this.columnValue = columnValue;
        this.lastUpdated = LocalDateTime.now();
    }

    public RPMethVALUES(
            String columnName,
            String columnValue
    ) {
        super(LinkType.No_LINK, 0);
        this.columnName = columnName;
        this.relationship = RelationOperator.EQUAL_TO;
        this.columnValue = columnValue;
        this.columnType = ColType.STRING;
        this.lastUpdated = LocalDateTime.now();
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public RelationOperator getRelationship() {
        return relationship;
    }

    public void setRelationship(RelationOperator relationship) {
        this.relationship = relationship;
    }

    public ColType getColumnType() {
        return columnType;
    }

    public void setColumnType(ColType columnType) {
        this.columnType = columnType;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
