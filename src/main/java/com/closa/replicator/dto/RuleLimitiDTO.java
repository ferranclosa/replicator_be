package com.closa.replicator.dto;

import javax.validation.constraints.NotNull;

public class RuleLimitiDTO extends TemplateRuleiDTO{

    @NotNull
    private Integer limitBy;

    public RuleLimitiDTO() {
        super();
    }

    public Integer getLimitBy() {
        return limitBy;
    }

    public void setLimitBy(Integer limitBy) {
        this.limitBy = limitBy;
    }
}

