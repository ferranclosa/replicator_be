package com.closa.replicator.throwables.exceptions;


import com.closa.replicator.throwables.AppException;
import com.closa.replicator.throwables.MessageCode;

import javax.print.attribute.standard.Severity;
import java.util.List;

public class ValidationJsonException extends AppException {
    private static final long serialVersionUID = 1L;

    public ValidationJsonException(String jsonField)
    {
        this(jsonField, (String) null, (String)null,(Severity) null);
    }

    public ValidationJsonException(Exception e) {
        super(e);
    }

    public ValidationJsonException(String jsonField, String code, String libelle, Severity severity){
        super( MessageCode.APP0003, "[ " + jsonField + " ] : " + libelle);
    }
    public ValidationJsonException(List<String> listOfExceptions){
        super( MessageCode.APP0003, listOfExceptions);
    }
}
