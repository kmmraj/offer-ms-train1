package quarkus.mservices.offer;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Path("/api/offers")
public class OfferResource {

    @ConfigProperty(name = "offer.default.travel.days", defaultValue = "30")
    int defaultTravelDays;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Offer> getOffers() {
        Offer offerOne = new Offer();
        offerOne.setId(UUID.randomUUID().toString().substring(0,8));
        offerOne.setCabinClass(CabinClassEnum.ECONOMY);
        offerOne.setDestination("MAD");
        offerOne.setFlightId(UUID.randomUUID().toString().substring(0,5));
        offerOne.setOrigin("BCN");
        offerOne.setDepartureDate(Date.from(Instant.now().plus(defaultTravelDays, ChronoUnit.DAYS)));

        return List.of(offerOne);
    }
}