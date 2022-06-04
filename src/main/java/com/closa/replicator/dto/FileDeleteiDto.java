package com.closa.replicator.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FileDeleteiDto {

    @NotNull
    @NotBlank
    private String requestCode;
    @NotNull
    @NotBlank
    private String fileName;

    public FileDeleteiDto() {
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


}
