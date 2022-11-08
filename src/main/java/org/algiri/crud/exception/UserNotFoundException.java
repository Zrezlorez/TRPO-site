package org.algiri.crud.exception;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("User not found");
    }
}
