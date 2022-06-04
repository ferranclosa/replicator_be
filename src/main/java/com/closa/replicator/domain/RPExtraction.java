package com.closa.replicator.domain;

import com.closa.replicator.domain.enums.LinkType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@Table(name = "rp_extr_rule")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "rp_type", discriminatorType = DiscriminatorType.STRING, length = 10)
public class RPExtraction implements EntityCommon {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "EXTR_ID")
    @TableGenerator(name = "EXTR_ID", table = "GEN_ID",
            pkColumnName = "key_name", valueColumnName = "key_val",
            pkColumnValue = "EXTR_ID", initialValue = 1, allocationSize = 5)
    private Long id;
    @Column(insertable = false, updatable = false)
    private String rp_type;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private RPFile rpFile;
    private Integer ordering = 0;
    @Enumerated(EnumType.STRING)
    private LinkType linkType;
    @Transient
    private String extMethod;

    public String getRp_type() {
        return rp_type;
    }

    public void setRp_type(String rp_type) {
        this.rp_type = rp_type;
    }

    public String getExtMethod() {
        return extMethod;
    }

    public void setExtMethod(String extMethod) {
        this.extMethod = extMethod;
    }

    public RPExtraction() {
    }

    public RPExtraction(LinkType linkType, Integer ordering) {
        this.ordering = ordering;
        this.linkType = linkType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RPFile getRpFile() {
        return rpFile;
    }

    public void setRpFile(RPFile rpFile) {
        this.rpFile = rpFile;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering + 1;
    }

    public LinkType getLinkType() {
        return linkType;
    }

    public void setLinkType(LinkType linkType) {
        this.linkType = linkType;
    }
}
