package com.celso.springrest.exceptions;

import java.io.Serializable;
import java.util.Date;

public class ExceptionsResponse implements Serializable {

    private Date timestamp;
    private String message;
    private String detail;

    public ExceptionsResponse() {
    }

    public ExceptionsResponse(Date timestamp, String message, String detail) {
        this.timestamp = timestamp;
        this.message = message;
        this.detail = detail;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
