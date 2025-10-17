package federicopini.B6_L5.repos;

import federicopini.B6_L5.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DipendenteRepository extends JpaRepository<Dipendente, UUID> {
}
