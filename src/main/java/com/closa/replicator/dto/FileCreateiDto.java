package com.closa.replicator.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FileCreateiDto {

    @NotNull
    @NotBlank
    private String requestCode;
    @NotNull
    @NotBlank
    private String fileName;
    @NotNull
    @Min(0)
    private Integer fileOrder;

    public FileCreateiDto() {
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

    public Integer getFileOrder() {
        return fileOrder;
    }

    public void setFileOrder(Integer fileOrder) {
        this.fileOrder = fileOrder;
    }
}
