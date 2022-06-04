package com.closa.replicator.dto;

import com.closa.replicator.domain.RPRequest;

import java.util.ArrayList;
import java.util.List;

public class RequestListoDTO extends TemplateoDTO {

    private List<RPRequest> requestList = new ArrayList<>();

    public RequestListoDTO() {
    }

    public List<RPRequest> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<RPRequest> requestList) {
        this.requestList = requestList;
    }
}
