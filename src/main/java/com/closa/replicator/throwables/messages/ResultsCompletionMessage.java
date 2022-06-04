package com.closa.replicator.throwables.messages;


import com.closa.replicator.throwables.AppMessage;
import com.closa.replicator.throwables.MessageCode;

public class ResultsCompletionMessage extends AppMessage {
    public ResultsCompletionMessage(String theWhat) {
        super(MessageCode.APP0009, theWhat);
    }
}
