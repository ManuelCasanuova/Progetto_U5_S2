package it.epicode.Progetto_U5_S2.dto;

import jakarta.validation.constraints.NotNull;

public record PrenotazioneDTO (

        String preferenza,
        @NotNull(message = "Viaggio obbligatorio")
        Long viaggioId,
        @NotNull(message = "Dipendente obbligatorio")
        Long dipendenteId) {

}
