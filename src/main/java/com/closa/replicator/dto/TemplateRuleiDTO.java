package com.closa.replicator.dto;

import com.closa.replicator.domain.RPExtraction;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TemplateRuleiDTO {

    @NotNull
    @NotBlank
    private String requestCode;

    @NotNull
    @NotBlank
    private String fileName;

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

    private RPExtraction extractionRule;

    public TemplateRuleiDTO() {
    }

    public RPExtraction getExtractionRule() {
        return extractionRule;
    }

    public void setExtractionRule(RPExtraction extractionRule) {
        this.extractionRule = extractionRule;
    }
}
