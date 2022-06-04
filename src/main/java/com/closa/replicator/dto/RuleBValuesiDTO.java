package com.closa.replicator.dto;

import com.closa.replicator.domain.enums.RelationOperator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RuleBValuesiDTO extends TemplateRuleiDTO {

    @NotNull
    @NotBlank
    public String columnName;
    @NotNull
    @NotBlank
    public String columnValue;
    @NotNull
    public RelationOperator relationOperator;

    public RuleBValuesiDTO() {
        super();
    }


}

