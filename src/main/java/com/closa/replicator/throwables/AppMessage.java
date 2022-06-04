package com.closa.replicator.throwables;


import com.closa.replicator.domain.EntityCommon;

public class AppMessage extends Throwable implements EntityCommon {

    private MessageCode messageCode;
    private String messageText;
    private String rCode;
    private String theWhat;

    public AppMessage(MessageCode messageCode, String theWhat) {
        this.messageCode = messageCode;
        this.messageText = "{" + messageCode.getmCode() + "} " + messageCode.getmMsg() + "[" + theWhat + "]";
        this.theWhat = theWhat;
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

    public String getTheWhat() {
        return theWhat;
    }

    public void setTheWhat(String theWhat) {
        this.theWhat = theWhat;
    }
}
