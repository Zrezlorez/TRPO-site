package org.algiri.crud.exception;


import java.time.LocalDateTime;

public class ApplicationError {
    private String appName;
    private String message;
    private LocalDateTime date;
    public ApplicationError(String appName, String message) {
        this.appName = appName;
        this.message = message;
        date = LocalDateTime.now();
    }
}
