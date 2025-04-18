package it.epicode.Progetto_U5_S2.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record DipendenteDTO(
        @NotEmpty(message = "Username obbligatorio")
        @Size(min = 3, max = 50, message = "Username deve essere tra 3 e 50 caratteri")
        String username,
        @NotEmpty(message = "Nome obbligatorio")
        @Size(min = 3, max = 50, message = "Nome deve essere tra 3 e 50 caratteri")
        String nome,
        @NotEmpty(message = "Cognome obbligatorio")
        @Size(min = 3, max = 50, message = "Cognome deve essere tra 3 e 50 caratteri")
        String cognome,
        @NotEmpty(message = "Email obbligatoria")
        @Size(min = 3, max = 50, message = "Email deve essere tra 3 e 50 caratteri")
        String email
) {
}
