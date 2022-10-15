package quarkus.mservices.offerprice;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.logging.Logger;
import quarkus.mservices.offerprice.repository.OfferPrice;
import quarkus.mservices.offerprice.repository.OfferPriceRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api")

public class OfferPriceResource {

    @Inject
    Logger logger;

    @Inject
    OfferPriceRepository offerPriceRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "OfferPrice by offerId", description = "Get OfferPrice by offerId")
    @Path("/offer-price/offer/{offerId}")
    public List<OfferPrice> getOfferPrice(@PathParam("offerId") String offerId) {
        logger.info("getOfferPrice with: " + offerId);
        return offerPriceRepository.getOffersPriceByOfferId(offerId);
    }
}