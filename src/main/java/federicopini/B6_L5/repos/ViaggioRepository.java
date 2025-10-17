package federicopini.B6_L5.repos;

import federicopini.B6_L5.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ViaggioRepository extends JpaRepository<Viaggio, UUID> {
}
