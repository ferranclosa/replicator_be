package com.closa.replicator.dto;

import com.closa.replicator.domain.enums.ExtractionMethods;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RuleCreateiDTO {

    @NotNull
    @NotBlank
    private String requestCode;
    @NotNull
    @NotBlank
    private String fileName;
    @NotNull
    private ExtractionMethods extractionMethod;

    private RuleLimitiDTO limitRule = new RuleLimitiDTO();

    private RuleBValuesiDTO valuesRule = new RuleBValuesiDTO();

    private RuleWhereiDTO whereRule = new RuleWhereiDTO();

    public RuleCreateiDTO() {
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ExtractionMethods getExtractionMethod() {
        return extractionMethod;
    }

    public void setExtractionMethod(ExtractionMethods extractionMethod) {
        this.extractionMethod = extractionMethod;
    }

    public RuleLimitiDTO getLimitRule() {
        return limitRule;
    }

    public void setLimitRule(RuleLimitiDTO limitRule) {
        this.limitRule = limitRule;
    }

    public RuleBValuesiDTO getValuesRule() {
        return valuesRule;
    }

    public void setValuesRule(RuleBValuesiDTO valuesRule) {
        this.valuesRule = valuesRule;
    }

    public RuleWhereiDTO getWhereRule() {
        return whereRule;
    }

    public void setWhereRule(RuleWhereiDTO whereRule) {
        this.whereRule = whereRule;
    }
}
