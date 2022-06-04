package com.closa.replicator.throwables.exceptions;


import com.closa.replicator.throwables.AppException;
import com.closa.replicator.throwables.MessageCode;

public class AccessDeniedException extends AppException {
    public AccessDeniedException(String theWhat) {
        super(MessageCode.AUT0005, theWhat);
    }
}
