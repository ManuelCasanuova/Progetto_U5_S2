package it.epicode.Progetto_U5_S2.repositories;

import it.epicode.Progetto_U5_S2.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViaggioRepository extends JpaRepository<Viaggio, Long>{
}
