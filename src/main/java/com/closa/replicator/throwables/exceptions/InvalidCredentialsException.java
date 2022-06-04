package com.closa.replicator.throwables.exceptions;


import com.closa.replicator.throwables.AppException;
import com.closa.replicator.throwables.MessageCode;

public class InvalidCredentialsException extends AppException {
    public InvalidCredentialsException(String theWhat) {
        super(MessageCode.AUT0003, theWhat);
    }
}
