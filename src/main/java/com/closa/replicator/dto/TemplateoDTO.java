package com.closa.replicator.dto;

import java.util.ArrayList;
import java.util.List;

public class TemplateoDTO {

    private String responseCode;
    private String responseMessage;
    private List<String> messageList = new ArrayList<>();

    public TemplateoDTO() {
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
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
