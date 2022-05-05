package com.data.exception;

import java.util.Date;

public class ErrorResponse {
    private Date timestamp;
    private String message;
    private String details;
    private int statusCode;

    public ErrorResponse(Date timestamp, String message, String string, int statusCode) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = string;
        this.statusCode = statusCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getDetails() {
        return details;
    }

}