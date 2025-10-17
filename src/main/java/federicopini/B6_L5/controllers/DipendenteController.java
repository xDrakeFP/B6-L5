package federicopini.B6_L5.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {
    @GetMapping
    public String getAll(){
        return "GET";
    }

    @GetMapping("/{dipendenteID}")
    public String findById(){
        return "GET ID";
    }
    @PostMapping
    public String createDipendene(){
        return "POST";
    }
    @PutMapping("/{dipendenteID}")
    public String updateDipendente(){
    return "PUT";
    }
    @DeleteMapping("/{dipendenteID}")
    public String deleteDipendente(){
        return "DELETE";
    }
    @PatchMapping("/{dipendenteID}/avatar")
    public String editAvatar(){
        return "PATCH";
    }
}
