package com.closa.replicator.dto;

import java.util.ArrayList;
import java.util.List;

public class TemplateoDTO {

    private String responseCode;
    private List<String> messageList = new ArrayList<>();

    public TemplateoDTO() {
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public List<String> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<String> messageList) {
        this.messageList = messageList;
    }
}
