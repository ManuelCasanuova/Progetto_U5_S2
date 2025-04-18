package it.epicode.Progetto_U5_S2.repositories;

import it.epicode.Progetto_U5_S2.entities.Dipendente;
import it.epicode.Progetto_U5_S2.entities.Prenotazione;
import it.epicode.Progetto_U5_S2.entities.Viaggio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long>{
    boolean existsByViaggio(Viaggio viaggio);

    Page<Prenotazione> findByDipendente(Dipendente dipendente, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Prenotazione p WHERE p.dipendente = :dipendente AND p" +
            ".viaggio.data = :data")
    boolean checkIfDipendenteIsNotAvailable(Dipendente dipendente, LocalDate date);
}

