package federicopini.B6_L5.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import federicopini.B6_L5.entities.Dipendente;
import federicopini.B6_L5.exceptions.BadRequestException;
import federicopini.B6_L5.exceptions.DataInUseException;
import federicopini.B6_L5.exceptions.NotFoundException;
import federicopini.B6_L5.payloads.NewDipendentePayload;
import federicopini.B6_L5.repos.DipendenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@Slf4j
public class DipendenteService {

    private static final long MAX_SIZE = 5 * 1024 * 1024; // 5 MB
    private static final List<String> ALLOWED_TYPES = List.of("image/png", "image/jpeg");

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private DipendenteRepository repo;

    public Page<Dipendente> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize>20) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return this.repo.findAll(pageable);
    }

    public Dipendente findById(UUID id){
        return repo.findById(id).orElseThrow(()-> new NotFoundException("Dipendente non trovato con id "+ id));
    }

    public Dipendente createDipentente(NewDipendentePayload body){
    if (repo.existsByEmail(body.getEmail())) throw new DataInUseException("Email già utilizzata");
    if (repo.existsByUsername(body.getUsername())) throw new DataInUseException("Username già utilizzato");
    Dipendente newDipendente = new Dipendente(body.getUsername(),body.getNome(), body.getCognome(), body.getEmail(), body.getAvatarURL());
    repo.save(newDipendente);
    return newDipendente;
    }

    public Dipendente updateDipendente(NewDipendentePayload body, UUID id){
        Dipendente found = this.findById(id);
        if(!found.getEmail().equals(body.getEmail())) {
            this.repo.findByEmail(body.getEmail()).ifPresent(dipendente -> {
                throw new BadRequestException("L'email "+dipendente.getEmail()+" è gia in uso");
            });
        }
        if(!found.getUsername().equals(body.getUsername())) {
            this.repo.findByUsername(body.getUsername()).ifPresent(dipendente -> {
                throw new BadRequestException("Username "+dipendente.getUsername()+" è gia in uso");
            });
        }
        found.setNome(body.getNome());
        found.setCognome(body.getCognome());
        found.setEmail(body.getEmail());
        found.setUsername(body.getUsername());
        found.setAvatarURL(body.getAvatarURL());
        Dipendente modifiedDipendente = this.repo.save(found);
        log.info("Modificato con successo il dipendente con id "+modifiedDipendente.getId());
        return modifiedDipendente;
    }
    public void deleteDipendente(UUID id){
        Dipendente found = this.findById(id);
        this.repo.delete(found);
        log.info("Eliminato con successo il dipendente con id "+found.getId());
    }

    public Dipendente updateAvatar(UUID id, MultipartFile file) {
        if(file.isEmpty()) throw new BadRequestException("File vuoto!");
        if(file.getSize() > MAX_SIZE) throw new BadRequestException("File troppo grande (inserisci un file sotto i 5 MB)");
        if(!ALLOWED_TYPES.contains(file.getContentType())) throw new BadRequestException("I formati permessi sono jpeg e png!");

        Dipendente found = this.findById(id);

        try{
            Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageURL = (String) result.get("url");
            found.setAvatarURL(imageURL);
            Dipendente modifiedUrlDip = this.repo.save(found);
            log.info("Modificato con successo l'avatar del dipendente con id "+modifiedUrlDip.getId());
            return found;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
}
}
