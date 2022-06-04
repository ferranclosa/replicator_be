package com.closa.replicator.throwables.exceptions;


import com.closa.replicator.throwables.AppException;
import com.closa.replicator.throwables.MessageCode;

public class InvalidInputFormat extends AppException {

    public InvalidInputFormat(String theWhat) {
        super(MessageCode.APP0003, theWhat);
    }
}
