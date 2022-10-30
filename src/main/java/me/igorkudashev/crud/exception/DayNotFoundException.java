package me.igorkudashev.crud.exception;

public class DayNotFoundException extends NotFoundException {
    public DayNotFoundException() {
        super("Day not found");
    }
}
