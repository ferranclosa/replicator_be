package com.closa.replicator.dto;


import javax.validation.constraints.NotNull;

public class RequestReadiDTO {

    @NotNull
    private String requestCode;

    public RequestReadiDTO() {
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }


}
