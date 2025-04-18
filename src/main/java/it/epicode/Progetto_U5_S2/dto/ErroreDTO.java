package it.epicode.Progetto_U5_S2.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ErroreDTO(String message, LocalDateTime timestamp) {
}
