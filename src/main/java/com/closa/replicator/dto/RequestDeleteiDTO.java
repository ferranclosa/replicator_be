package com.closa.replicator.dto;


import com.sun.istack.NotNull;

import javax.validation.constraints.NotBlank;

public class RequestDeleteiDTO {

    @NotNull
    @NotBlank
    private String requestCode;

    public RequestDeleteiDTO() {
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }


}
