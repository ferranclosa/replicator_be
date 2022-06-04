package com.closa.replicator.throwables.exceptions;


import com.closa.replicator.throwables.AppException;
import com.closa.replicator.throwables.MessageCode;

public class ItemNotFoundException extends AppException {
    public ItemNotFoundException( String theWhat) {
        super(MessageCode.APP0001, theWhat);
    }
}
