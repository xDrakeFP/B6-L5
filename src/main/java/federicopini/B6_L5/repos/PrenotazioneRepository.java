package federicopini.B6_L5.repos;

import federicopini.B6_L5.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {
    boolean existsByDipendente_IdAndViaggio_Data(UUID id, LocalDate data);
}
