package quarkus.mservices.offer;

import lombok.*;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Offer {
    @Id
    private String id;
    private String origin;
    private String destination;
    @JsonbProperty("departure_date")
    private Date departureDate;
    @JsonbProperty("cabin_class")
    private CabinClassEnum cabinClass;
    @JsonbProperty("flight_id")
    private String flightId;
}
