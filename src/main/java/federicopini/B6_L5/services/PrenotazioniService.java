package federicopini.B6_L5.services;

import federicopini.B6_L5.entities.Dipendente;
import federicopini.B6_L5.entities.Prenotazione;
import federicopini.B6_L5.entities.Viaggio;
import federicopini.B6_L5.exceptions.DateException;
import federicopini.B6_L5.exceptions.NotFoundException;
import federicopini.B6_L5.payloads.NewNota;
import federicopini.B6_L5.payloads.NewPrenotazionePayload;
import federicopini.B6_L5.repos.DipendenteRepository;
import federicopini.B6_L5.repos.PrenotazioneRepository;
import federicopini.B6_L5.repos.ViaggioRepository;
import jakarta.validation.constraints.Max;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class PrenotazioniService {
    @Autowired
    private PrenotazioneRepository repo;
    @Autowired
    private ViaggioRepository viaggiRepo;
    @Autowired
    private DipendenteRepository dipRepo;
    @Autowired
    public ViaggioService viaggioService;
    @Autowired
    public DipendenteService dipendenteService;

    public Page<Prenotazione> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize>20) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return this.repo.findAll(pageable);
    }

    public Prenotazione findById(UUID id){
        return repo.findById(id).orElseThrow(()-> new NotFoundException("Prenotazione non trovata con id "+ id));
    }

    public Prenotazione creaPrenotazione(NewPrenotazionePayload body){
        Viaggio foundViaggio = viaggioService.findById(body.getViaggioId());
        Dipendente foundDipendente = dipendenteService.findById(body.getDipendenteId());

        if(repo.existsByDipendente_IdAndViaggio_Data(body.getDipendenteId(), foundViaggio.getData())){
            throw new DateException("Il Dipendente ha già una prenotazione nella data indicata");
        }

        Prenotazione newPrenotazione = new Prenotazione(foundViaggio,foundDipendente,body.getNote());
        log.info("Prenotazione per il viaggio a "+foundViaggio.getDestinazione()+" salvata correttamente");
        return this.repo.save(newPrenotazione);
    }
    public Prenotazione updateNote(NewNota body, UUID id){
        Prenotazione found = this.findById(id);
        found.setNote(body.getNote());
        return this.repo.save(found);
    }

    public void deletePrenotazione(UUID id){
        Prenotazione found = this.findById(id);
        this.repo.delete(found);
        log.info("Eliminato con successo la prenotazione con id "+found.getId());
    }
}
