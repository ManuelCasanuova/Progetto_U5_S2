package it.epicode.Progetto_U5_S2.services;

import it.epicode.Progetto_U5_S2.dto.PrenotazioneDTO;
import it.epicode.Progetto_U5_S2.entities.Dipendente;
import it.epicode.Progetto_U5_S2.entities.Prenotazione;
import it.epicode.Progetto_U5_S2.entities.Viaggio;
import it.epicode.Progetto_U5_S2.exceptions.BadRequestException;
import it.epicode.Progetto_U5_S2.exceptions.NotFoundException;
import it.epicode.Progetto_U5_S2.repositories.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository  prenotazioneRepository;
    @Autowired
    private DipendenteService dipendenteService;
    @Autowired
    private ViaggioService viaggioService;

    public Page<Prenotazione> findAllPrenotazioni(int page, int size, String sortBy, Long dipendenteId) {
        if (dipendenteId != null) {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            Dipendente dipendente = dipendenteService.findDipendenteById(dipendenteId);
            return prenotazioneRepository.findByDipendente(dipendente, pageable);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione savePrenotazione(PrenotazioneDTO body) {
        Dipendente dipendente = dipendenteService.findDipendenteById(body.dipendenteId());
        Viaggio Viaggio = viaggioService.findViaggioById(body.viaggioId());
        if (prenotazioneRepository.existsByViaggio(Viaggio))
            throw new BadRequestException("Viaggio already assigned");
        if (prenotazioneRepository.checkIfDipendenteIsNotAvailable(dipendente, Viaggio.getData()))
            throw new BadRequestException("dipendente unavailable for Viaggio's date");
       Prenotazione prenotazione= new Prenotazione(Viaggio, dipendente);
        if (body.preferenza() != null) prenotazione.setPreferenza(body.preferenza());
        else prenotazione.setPreferenza("N/A");
        return prenotazioneRepository.save(prenotazione);
    }

    public Prenotazione findPrenotazioniById(Long prenotazioneId) {
        return prenotazioneRepository.findById(prenotazioneId).orElseThrow(() -> new NotFoundException(prenotazioneId, "prenotazioni"));
    }

    public void findPrenotazioniByIdAndDelete(Long prenotazioneId) {
        Prenotazione prenotazione = findPrenotazioniById(prenotazioneId);
        prenotazioneRepository.delete(prenotazione);
    }
}