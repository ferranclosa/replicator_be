package com.closa.replicator.throwables.exceptions;

import com.closa.replicator.throwables.AppException;
import com.closa.replicator.throwables.MessageCode;

public class UserNotFoundException extends AppException {
    public UserNotFoundException(String theWhat) {
        super(MessageCode.AUT0001, theWhat);
    }
}
