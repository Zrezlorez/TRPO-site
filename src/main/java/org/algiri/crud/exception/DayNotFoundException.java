package org.algiri.crud.exception;

public class DayNotFoundException extends NotFoundException {
    public DayNotFoundException() {
        super("Day not found");
    }
}
