package it.epicode.Progetto_U5_S2.exceptions;

import it.epicode.Progetto_U5_S2.dto.ErroreDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroreDTO handleNotFound(NotFoundException e) {
        return new ErroreDTO(e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroreDTO handleBadRequest(BadRequestException e) {
        return new ErroreDTO(e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroreDTO handleGeneric(Exception e) {
        e.printStackTrace();
        return new ErroreDTO("An internal error occurred, sorry", LocalDateTime.now());
    }
}