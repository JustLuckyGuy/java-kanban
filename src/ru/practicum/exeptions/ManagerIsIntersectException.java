package ru.practicum.exeptions;

public class ManagerIsIntersectException extends RuntimeException {
    public ManagerIsIntersectException(String message) {
        super(message);
    }
}
