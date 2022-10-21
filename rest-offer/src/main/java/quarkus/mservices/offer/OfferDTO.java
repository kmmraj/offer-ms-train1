package quarkus.mservices.offer;

import lombok.*;

//import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OfferDTO {
    @Id
    private String id;
    private String origin;
    private String destination;
//    @JsonbProperty("travel_date")
    private LocalDate travelDate;
//    @JsonbProperty("cabin_class")
    private CabinClassEnum cabinClass;
//    @JsonbProperty("flight_id")
    private String flightId;
}
