package com.closa.replicator.throwables.exceptions;


import com.closa.replicator.throwables.AppException;
import com.closa.replicator.throwables.MessageCode;

public class DatabaseRelatedException extends AppException {
    public DatabaseRelatedException(String theWhat) {
        super(MessageCode.APP0008, theWhat);
    }
}
