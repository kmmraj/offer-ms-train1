package quarkus.mservices.offer.repository;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;
import quarkus.mservices.offer.CabinClassEnum;

import javax.json.bind.annotation.JsonbProperty;
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
    @JsonbProperty("start_date")
    private LocalDate startDate;
    @JsonbProperty("end_date")
    private LocalDate endDate;
    @JsonbProperty("cabin_class")
    private CabinClassEnum cabinClass;
    @JsonbProperty("flight_id")
    private String flightId;
}
