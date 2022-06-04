package com.closa.replicator.throwables.exceptions;


import com.closa.replicator.throwables.AppException;
import com.closa.replicator.throwables.MessageCode;

public class UnexpectedNonEmptyResultException extends AppException {

    public UnexpectedNonEmptyResultException(String theWhat) {
        super(MessageCode.APP0007, theWhat);
    }
}
