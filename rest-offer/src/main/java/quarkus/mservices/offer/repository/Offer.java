package quarkus.mservices.offer.repository;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;
import quarkus.mservices.offer.CabinClassEnum;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Offer extends PanacheEntityBase {
    @Id
    private String id;
    private String origin;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private CabinClassEnum cabinClass;
    private String flightId;
}
