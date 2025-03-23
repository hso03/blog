package com.nhnacademy.blog.common.advice;

public class CommonException {
    private final String message;
    private final int statusCode;
    private final String uri;


    public CommonException(String message, int statusCode, String uri) {
        this.message = message;
        this.statusCode = statusCode;
        this.uri = uri;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getUri() {
        return uri;
    }
}
