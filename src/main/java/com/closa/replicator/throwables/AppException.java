package com.closa.replicator.throwables;

import com.closa.replicator.domain.EntityCommon;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class AppException extends Exception implements EntityCommon {
    private MessageCode messageCode;
    private String messageText;
    private String rCode;
    @JsonIgnore
    private Exception e;
    private String theWhat;
    private HttpStatus httpStatus;
    private List<String> multiExceptions = new ArrayList<>();

    public List<String> getMultiExceptions() {
        return multiExceptions;
    }

    public void setMultiExceptions(List<String> multiExceptions) {
        this.multiExceptions = multiExceptions;
    }

    public AppException(Exception e ){
        this.e = e;
    }

    public AppException(String message, MessageCode messageCode, String theWhat) {
        super(message);
        this.messageCode = messageCode;
        this.theWhat = theWhat;
        this.messageText = "{" + messageCode.getmCode() + "} " + messageCode.getmMsg() + " [" + theWhat + "]";
    }

    public AppException(MessageCode messageCode, String rCode, String theWhat) {
        this.messageCode = messageCode;
        this.rCode = rCode;
        this.theWhat = theWhat;
        this.messageText = "{" + messageCode.getmCode() + "} " + messageCode.getmMsg() + " [" + theWhat + "]";
    }

    public AppException(MessageCode messageCode, String theWhat) {
        this.messageCode = messageCode;
        this.theWhat = theWhat;
        this.messageText = "{" + messageCode.getmCode() + "} " + messageCode.getmMsg() + " [" + theWhat + "]";
    }
    public AppException(MessageCode messageCode, List<String> theExceptions) {
        this.messageCode = messageCode;
        this.multiExceptions = theExceptions;
        this.messageText = "{" + messageCode.getmCode() + "} " + messageCode.getmMsg() ;
    }

    public MessageCode getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(MessageCode messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getrCode() {
        return rCode;
    }

    public void setrCode(String rCode) {
        this.rCode = rCode;
    }

    public Exception getE() {
        return e;
    }

    public void setE(Exception e) {
        this.e = e;
    }

    public String getTheWhat() {
        return theWhat;
    }

    public void setTheWhat(String theWhat) {
        this.theWhat = theWhat;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
