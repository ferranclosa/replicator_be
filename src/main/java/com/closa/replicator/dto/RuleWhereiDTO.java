package com.closa.replicator.dto;

public class RuleWhereiDTO extends TemplateRuleiDTO{

    private String whereStatement;

    public RuleWhereiDTO() {
        super();
    }

    public String getWhereStatement() {
        return whereStatement;
    }

    public void setWhereStatement(String whereStatement) {
        this.whereStatement = whereStatement;
    }
}

