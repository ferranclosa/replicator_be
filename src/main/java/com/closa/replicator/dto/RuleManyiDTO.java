package com.closa.replicator.dto;

import org.apache.commons.collections.ArrayStack;

import java.util.ArrayList;
import java.util.List;

public class RuleManyiDTO extends TemplateRuleiDTO {
    public RuleManyiDTO() {
        super();
    }

    private List<ManyDetails> manys = new ArrayList<>();

    public List<ManyDetails> getManys() {
        return manys;
    }

    public void setManys(List<ManyDetails> manys) {
        this.manys = manys;
    }

    private class ManyDetails {
        private String manyTable;
        private String manyColumn;
        private String oneColumn;

        public ManyDetails(String manyTable, String manyColumn, String oneColumn) {
            this.manyTable = manyTable;
            this.manyColumn = manyColumn;
            this.oneColumn = oneColumn;
        }
    }
}

