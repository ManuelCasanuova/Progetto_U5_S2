package it.epicode.Progetto_U5_S2.repositories;

import it.epicode.Progetto_U5_S2.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente, Long>{

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
