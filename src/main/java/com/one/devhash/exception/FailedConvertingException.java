package com.one.devhash.exception;

import com.one.devhash.global.error.exception.ErrorCode;
import com.one.devhash.global.error.exception.InvalidValueException;

public class FailedConvertingException extends InvalidValueException {
    public FailedConvertingException(ErrorCode errorCode) {
        super(errorCode);
    }
}
