package it.epicode.Progetto_U5_S2.exceptions;



public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id, String resource) {
        super(resource + " con id " + id + " non esistente");
    }
}