package com.vitorfurini.codegroup.exception.custom;

import com.vitorfurini.codegroup.exception.models.ErrorResponse;
import lombok.Getter;

@Getter
public class ServiceException extends Exception {

    private final String message;
    private final ErrorResponse errorResponse;
    private final String technicalMessage;

    public ServiceException(Exception e, String message) {
        super(e);
        this.message = message;
        this.technicalMessage = null;
        this.errorResponse = null;
    }

    public ServiceException(String message, String technicalMessage) {
        this.message = message;
        this.technicalMessage = technicalMessage;
        this.errorResponse = null;
    }

    public ServiceException(String message, String technicalMessage, ErrorResponse errorResponse) {
        this.message = message;
        this.technicalMessage = technicalMessage;
        this.errorResponse = errorResponse;
    }

    public ServiceException(String message) {
        this.message = message;
        this.technicalMessage = null;
        this.errorResponse = null;
    }

}
