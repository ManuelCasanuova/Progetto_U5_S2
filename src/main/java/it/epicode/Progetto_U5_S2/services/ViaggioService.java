package it.epicode.Progetto_U5_S2.services;

import it.epicode.Progetto_U5_S2.dto.StatoDTO;
import it.epicode.Progetto_U5_S2.dto.ViaggioDTO;
import it.epicode.Progetto_U5_S2.entities.Viaggio;
import it.epicode.Progetto_U5_S2.enums.StatoViaggio;
import it.epicode.Progetto_U5_S2.exceptions.BadRequestException;
import it.epicode.Progetto_U5_S2.exceptions.NotFoundException;
import it.epicode.Progetto_U5_S2.repositories.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class ViaggioService {


    @Autowired
    private ViaggioRepository viaggioRepository;

    @Autowired
    private List<DateTimeFormatter> formatters;


    public Page<Viaggio> findAllViaggi(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return viaggioRepository.findAll(pageable);
    }

    public Viaggio saveViaggio(ViaggioDTO body) {
        Viaggio viaggio = new Viaggio(body.destinazione());
        viaggio.setData(validateDate(body.data()));
        return viaggioRepository.save(viaggio);
    }

    public Viaggio findViaggioById(Long ViaggioId) {
        return viaggioRepository.findById(ViaggioId).orElseThrow(() -> new NotFoundException(ViaggioId, "Viaggio"));
    }

    public Viaggio findViaggioByIdAndUpdate(Long viaggioId, ViaggioDTO body) {
        Viaggio cercato = findViaggioById(viaggioId);
        if (cercato.getPrenotazione() != null)
            throw new BadRequestException("Viaggio assegnato a una prenotazione. Per modificare il Viaggio, cancella la prenotazione.");
        if (cercato.getData().isBefore(LocalDate.now()))
            throw new BadRequestException("Cannot modify a Viaggio that is past in time");
        cercato.setData(validateDate(body.data()));
        cercato.setDestinazione(body.destinazione());
        return viaggioRepository.save(cercato);
    }

    public void findViaggioByIdAndDelete(Long ViaggioId) {
        Viaggio searched = findViaggioById(ViaggioId);
        viaggioRepository.delete(searched);
    }

    public Viaggio findByIdAndUpdateStato(Long ViaggioId, StatoDTO body) {
        Viaggio searched = findViaggioById(ViaggioId);
        if (StatoViaggio.valueOf(body.status()).equals(searched.getStatoViaggio()))
            throw new BadRequestException("Cannot provide the same status of the actual one");
        System.out.println(StatoViaggio.valueOf(body.status()));
        searched.setStatoViaggio(StatoViaggio.valueOf(body.status()));
        return viaggioRepository.save(searched);
    }

    private LocalDate validateDate(String date) {
        for (DateTimeFormatter formatter : formatters) {
            try {
                LocalDate dateFormatted = LocalDate.parse(date, formatter);
                if (dateFormatted.isBefore(LocalDate.now()))
                    throw new BadRequestException("Date must be in the future");
                return dateFormatted;
            } catch (DateTimeParseException ignored) {

            }
        }
        throw new BadRequestException("Format date not supported");
    }
}
