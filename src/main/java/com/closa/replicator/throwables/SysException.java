package com.closa.replicator.throwables;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SysException extends AppException {


    public SysException(Exception e) {
        super(e);
    }

    private String returnCode;
    private String returnLabel;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> returnMessages = new ArrayList<>();


    public SysException(MessageCode messageCode, String theWhat, Exception e) {
        super(messageCode,  theWhat);
        this.returnCode = messageCode.getrCode();
        this.returnLabel = messageCode.getmMsg();
        this.returnMessages = Arrays.asList(ExceptionUtils.getRootCauseStackTrace(e));
    }
}
