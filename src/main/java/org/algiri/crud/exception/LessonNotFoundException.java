package org.algiri.crud.exception;

public class LessonNotFoundException extends NotFoundException {
    public LessonNotFoundException() {
        super("Lesson not found");
    }
}
