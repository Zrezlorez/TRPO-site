package org.algiri.crud.exception;

public class GroupExistsException extends ExistsException {
    public GroupExistsException() {
        super("This group already exists.");
    }
}
