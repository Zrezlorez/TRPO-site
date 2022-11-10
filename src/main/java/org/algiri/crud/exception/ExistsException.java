package org.algiri.crud.exception;

public class ExistsException extends RuntimeException{
    public ExistsException(String message) {
        super(message);
    }
}
