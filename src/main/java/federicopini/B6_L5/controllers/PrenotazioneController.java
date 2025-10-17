package federicopini.B6_L5.controllers;


import federicopini.B6_L5.entities.Prenotazione;

import federicopini.B6_L5.exceptions.ValidationException;
import federicopini.B6_L5.payloads.NewNota;
import federicopini.B6_L5.payloads.NewPrenotazionePayload;
import federicopini.B6_L5.services.PrenotazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioniService service;

    @GetMapping
    public Page<Prenotazione> findAll(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id")String sortBy
    ){
        return this.service.findAll(page,size,sortBy);
    }

    @GetMapping("/{id}")
    public Prenotazione findById(@PathVariable UUID id) {
        return this.service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione createPrenotazione(@RequestBody @Validated NewPrenotazionePayload body, BindingResult result){
        if(result.hasErrors()){
            throw new ValidationException(result.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        return this.service.creaPrenotazione(body);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePrenotazione(@PathVariable UUID id){
        this.service.deletePrenotazione(id);
    }
    @PatchMapping("/{id}/note")
    public Prenotazione updateNote(@PathVariable UUID id, @RequestBody @Validated NewNota body, BindingResult result){
        if(result.hasErrors()){
            throw new ValidationException(result.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        return this.service.updateNote(body,id);
    }


}
