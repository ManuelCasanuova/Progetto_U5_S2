package it.epicode.Progetto_U5_S2.dto;

import jakarta.validation.constraints.Pattern;

public record StatoDTO(
        @Pattern(regexp = "IN_PROGRAM|ASSIGNED|COMPLETED", message = "Status must be IN_PROGESS, ASSIGNED or " +
                "COMPLETED") String status) {
}