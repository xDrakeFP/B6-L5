package federicopini.B6_L5.entities;

import federicopini.B6_L5.entities.enums.StatoViaggio;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Viaggio {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String destinazione;
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    @Column(name = "stato_viaggio")
    private StatoViaggio statoViaggio;
}
