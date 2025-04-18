package it.epicode.Progetto_U5_S2.controllers;

import it.epicode.Progetto_U5_S2.dto.DipendenteDTO;
import it.epicode.Progetto_U5_S2.entities.Dipendente;
import it.epicode.Progetto_U5_S2.exceptions.BadRequestException;
import it.epicode.Progetto_U5_S2.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class DipendenteController {
    @Autowired
    private DipendenteService dipendenteService;

    @GetMapping
    public List<Dipendente> getDipendenti() {
        return dipendenteService.findAllDipendenti();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente saveDipendente(@RequestBody @Validated DipendenteDTO body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message =
                    bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
            throw new BadRequestException(message);
        }
        return dipendenteService.saveDipendente(body);
    }

    @GetMapping("/{employeeId}")
    public Dipendente findEmployee(@PathVariable Long dipendenteId) {
        return dipendenteService.findDipendenteById(dipendenteId);
    }

    @PutMapping("/{Id}")
    public Dipendente updateDipendente(@PathVariable Long dipendenteId, @RequestBody @Validated DipendenteDTO body,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message =
                    bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
            throw new BadRequestException(message);
        }
        return dipendenteService.findDipendenteByIdAndUpdate(dipendenteId, body);
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long dipenteId) {
        dipendenteService.findDipendenteByIdAndDelete(dipenteId);
    }

    @PatchMapping("/{employeeId}/avatar")
    public void uploadAvatar(@RequestParam("avatar") MultipartFile file, @PathVariable Long dipenteId) {
        dipendenteService.uploadAvatar(file, dipenteId);
    }
}