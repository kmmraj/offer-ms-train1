package quarkus.mservices.offer.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.List;


@ApplicationScoped
public class OfferRepository implements PanacheRepositoryBase<Offer, String> {

    public List<Offer> getOffersByOriginAndDestination(String origin, String destination) {
        return list("origin = ?1 and destination = ?2", origin, destination);
    }

    public List<Offer> getOffersByOriginAndDestinationAndTravelDate(String origin, String destination, LocalDate travelDate) {
        return list("origin = ?1 and destination = ?2 and startDate <= ?3 and endDate >= ?3", origin, destination, travelDate);
    }

}



