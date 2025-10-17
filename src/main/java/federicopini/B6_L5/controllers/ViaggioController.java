package federicopini.B6_L5.controllers;

import federicopini.B6_L5.entities.Dipendente;
import federicopini.B6_L5.entities.Viaggio;
import federicopini.B6_L5.exceptions.ValidationException;
import federicopini.B6_L5.payloads.NewViaggioPayload;
import federicopini.B6_L5.services.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/viaggi")
public class ViaggioController {

    @Autowired
    private ViaggioService service;

    @GetMapping
    public Page<Viaggio> findAll(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id")String sortBy){
        return this.service.findAll(page,size,sortBy);
    }
    @GetMapping("/{id}")
    public Viaggio findById(@PathVariable UUID id){
        return this.service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio createViaggio(@RequestBody @Validated NewViaggioPayload body, BindingResult result){
        if(result.hasErrors()){
            throw new ValidationException(result.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        return this.service.createViaggio(body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteViaggio(@PathVariable UUID id){
        this.service.deleteViaggio(id);
    }

}
