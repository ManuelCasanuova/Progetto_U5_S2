package it.epicode.Progetto_U5_S2.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@Table(name = "prenotazioni")

public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long prenotazioneId;

    @Column(nullable = false)
    private LocalDate dataRichiesta;

    @Column(nullable = false, length = 50)
    private String preferenza;

    @OneToOne
    @JoinColumn(name = "viaggio_id", nullable = false)
    private Viaggio viaggio;


    @ManyToOne
    @JoinColumn(name = "dipendente_id")
    private Dipendente dipendente;

    public Prenotazione(Viaggio viaggio, Dipendente dipendente) {
        this.viaggio = viaggio;
        this.dipendente = dipendente;
        this.dataRichiesta = LocalDate.now();
    }


}