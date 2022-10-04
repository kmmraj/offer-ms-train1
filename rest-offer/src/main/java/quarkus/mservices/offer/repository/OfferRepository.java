package quarkus.mservices.offer.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import quarkus.mservices.offer.Offer;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;


@ApplicationScoped
public class OfferRepository  implements PanacheRepositoryBase<Offer, String> {

    public List<Offer> getOffersByFlightId(String origin, String destination) {
        return list("origin = ?1 and destination = ?2", origin, destination);
    }

}



