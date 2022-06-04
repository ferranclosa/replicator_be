package com.closa.replicator.throwables.exceptions;


import com.closa.replicator.throwables.AppException;
import com.closa.replicator.throwables.MessageCode;

public class UserNotActiveException extends AppException {
    public UserNotActiveException(String theWhat) {
        super(MessageCode.AUT0002, theWhat);
    }
}
