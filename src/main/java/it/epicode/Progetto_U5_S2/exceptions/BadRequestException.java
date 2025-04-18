package it.epicode.Progetto_U5_S2.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String e) {
        super(e);
    }
}