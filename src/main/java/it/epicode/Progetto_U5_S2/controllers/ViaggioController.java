package it.epicode.Progetto_U5_S2.controllers;

import it.epicode.Progetto_U5_S2.dto.StatoDTO;
import it.epicode.Progetto_U5_S2.dto.ViaggioDTO;
import it.epicode.Progetto_U5_S2.entities.Viaggio;
import it.epicode.Progetto_U5_S2.exceptions.BadRequestException;
import it.epicode.Progetto_U5_S2.services.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/trips")
public class ViaggioController {
    @Autowired
    private ViaggioService viaggioService;

    @GetMapping
    public Page<Viaggio> getAllTrips(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "date") String sortBy) {
        return viaggioService.findAllViaggi(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio saveTrip(@RequestBody @Validated ViaggioDTO body, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", ")));
        return viaggioService.saveViaggio(body);
    }

    @PatchMapping("/{tripId}/status")
    public Viaggio modifyStatus(@PathVariable Long viaggioId, @RequestBody @Validated StatoDTO body,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", ")));
        return viaggioService.findByIdAndUpdateStato(viaggioId, body);
    }

    @GetMapping("/{tripId}")
    public Viaggio getSingleTrip(@PathVariable Long viaggioId) {
        return viaggioService.findViaggioById(viaggioId);
    }

    @PutMapping("/{tripId}")
    public Viaggio modifyTrip(@PathVariable Long viaggioId, @RequestBody @Validated ViaggioDTO body,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", ")));
        return viaggioService.findViaggioByIdAndUpdate(viaggioId, body);
    }

    @DeleteMapping("/{tripId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrip(@PathVariable Long viaggioId) {
        viaggioService.findViaggioByIdAndDelete(viaggioId);
    }
}