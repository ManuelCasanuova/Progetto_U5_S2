package it.epicode.Progetto_U5_S2.controllers;

import it.epicode.Progetto_U5_S2.dto.PrenotazioneDTO;
import it.epicode.Progetto_U5_S2.entities.Prenotazione;
import it.epicode.Progetto_U5_S2.exceptions.BadRequestException;
import it.epicode.Progetto_U5_S2.services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
public class PrenotazioneController {
    @Autowired
    private PrenotazioneService prenotazioneService;


    @GetMapping
    public Page<Prenotazione> getAllBookings(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "trip.date") String sortBy,
                                             @RequestParam(required = false) Long dipendenteId) {
        return prenotazioneService.findAllPrenotazioni(page, size, sortBy, dipendenteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione savePrenotazione(@RequestBody @Validated PrenotazioneDTO body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message =
                    bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
            throw new BadRequestException(message);
        }
        return prenotazioneService.savePrenotazione(body);
    }

    @GetMapping("/{bookingId}")
    public Prenotazione getSingleBooking(@PathVariable Long prenotazioneId) {
        return prenotazioneService.findPrenotazioniById(prenotazioneId);
    }

    @DeleteMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooking(@PathVariable Long prenotazioneId) {
        prenotazioneService.findPrenotazioniByIdAndDelete(prenotazioneId);
    }
}