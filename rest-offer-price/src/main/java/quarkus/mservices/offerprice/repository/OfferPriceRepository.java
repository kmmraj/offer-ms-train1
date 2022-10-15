package quarkus.mservices.offerprice.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;


@ApplicationScoped
public class OfferPriceRepository implements PanacheRepositoryBase<OfferPrice, String> {

    public List<OfferPrice> getOffersPriceByOfferId(String offerId) {
        return list("offerid = ?1", offerId);
    }

}



