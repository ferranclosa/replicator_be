package com.closa.replicator.throwables.exceptions;


import com.closa.replicator.throwables.AppException;
import com.closa.replicator.throwables.MessageCode;

public class UnexpectedEmptyResultException extends AppException {

    public UnexpectedEmptyResultException(String theWhat) {
        super(MessageCode.APP0006, theWhat);
    }
}
