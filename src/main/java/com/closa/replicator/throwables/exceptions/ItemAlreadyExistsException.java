package com.closa.replicator.throwables.exceptions;


import com.closa.replicator.throwables.AppException;
import com.closa.replicator.throwables.MessageCode;

public class ItemAlreadyExistsException extends AppException {
    public ItemAlreadyExistsException( String theWhat) {
        super(MessageCode.APP0002, theWhat);
    }
}
