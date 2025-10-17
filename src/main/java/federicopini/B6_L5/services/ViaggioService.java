package federicopini.B6_L5.services;

import federicopini.B6_L5.entities.Viaggio;
import federicopini.B6_L5.entities.enums.StatoViaggio;
import federicopini.B6_L5.exceptions.DateException;
import federicopini.B6_L5.exceptions.NotFoundException;
import federicopini.B6_L5.payloads.NewViaggioPayload;
import federicopini.B6_L5.repos.ViaggioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Slf4j
public class ViaggioService {

    @Autowired
    private ViaggioRepository repo;

    public Page<Viaggio> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 20) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return this.repo.findAll(pageable);
    }

    public Viaggio findById(UUID id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Viaggio non trovato con id " + id));
    }

    public Viaggio createViaggio(NewViaggioPayload body) {

        if (body.getData().isBefore(LocalDate.now()))
            throw new DateException("La data inserita non può essere nel passato");
        Viaggio newViaggio = new Viaggio(body.getDestinazione(), body.getData(), body.getStatoViaggio());
        return this.repo.save(newViaggio);
    }

    public Viaggio updateViaggio(NewViaggioPayload body, UUID id) {
        Viaggio found = this.findById(id);

        if (body.getStatoViaggio().equals(StatoViaggio.COMPLETATO))
            throw new DateException("Un viaggio completato non può avere le sue informazione aggiornate");
        if (body.getData().isBefore(LocalDate.now()))
            throw new DateException("La data inserita non può essere nel passato");
        found.setData(body.getData());
        found.setDestinazione(body.getDestinazione());
        Viaggio updatedViaggio = this.repo.save(found);
        log.info("Modificato con successo il viaggio con id " + updatedViaggio.getId());
        return updatedViaggio;
    }

    public void deleteViaggio(UUID id) {
        Viaggio found = this.findById(id);
        this.repo.delete(found);
        log.info("Eliminato con successo il viaggio con id "+found.getId());
    }

}

