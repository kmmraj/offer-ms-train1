package quarkus.mservices.offer;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.cache.NoCache;
import quarkus.mservices.offer.repository.OfferRepository;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Tag(name = "Resource for Offer APIs")
@Schema(description = "OfferResource API")
@Path("/api")
public class OfferResource {


    @Inject
    Logger logger;
    @Inject
    OfferRepository offerRepository;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/offers/orig/{origin}/dest/{destination}")
    @NoCache
    //@Authenticated
    @PermitAll
    public List<Offer> getOffers(@PathParam("origin") String origin, @PathParam("destination") String destination) {
        logger.info("getOffers with: " + origin + " and " + destination);
        return offerRepository.getOffersByFlightId(origin, destination);
    }

}