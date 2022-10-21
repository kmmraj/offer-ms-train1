package quarkus.mservices.offerprice.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class OfferPriceRepository implements PanacheRepositoryBase<OfferPrice, String> {

    public OfferPrice getOffersPriceByOfferId(String offerId) {
        //return list("offerid = ?1", offerId);
        return find("offerid", offerId).firstResult();
    }

}



