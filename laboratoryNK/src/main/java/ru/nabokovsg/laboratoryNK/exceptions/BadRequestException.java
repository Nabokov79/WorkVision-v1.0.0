package ru.nabokovsg.laboratoryNK.exceptions;
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
