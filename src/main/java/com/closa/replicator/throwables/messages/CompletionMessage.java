package com.closa.replicator.throwables.messages;


import com.closa.replicator.throwables.AppMessage;
import com.closa.replicator.throwables.MessageCode;

public class CompletionMessage extends AppMessage {
    public CompletionMessage( String theWhat) {
        super(MessageCode.APP0000, theWhat);
    }
}
