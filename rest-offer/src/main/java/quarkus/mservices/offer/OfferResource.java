package quarkus.mservices.offer;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.cache.NoCache;
import quarkus.mservices.offer.repository.Offer;
import quarkus.mservices.offer.repository.OfferRepository;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        return offerRepository.getOffersByOriginAndDestination(origin, destination);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/offers/orig/{origin}/dest/{destination}/date/{travelDate}")
    @NoCache
    //@Authenticated
    @PermitAll
    public List<OfferDTO> getOffers(@PathParam("origin") String origin,
                                 @PathParam("destination") String destination,
                                 @PathParam("travelDate") String travelDate) {
        LocalDate localDate = LocalDate.parse(travelDate, DateTimeFormatter.ISO_LOCAL_DATE);
        logger.info("getOffers with: " + origin + " and " + destination + " and " + localDate);
        List<Offer> offerList = offerRepository.getOffersByOriginAndDestinationAndTravelDate(origin, destination,localDate);
        return offerList.stream()
                .map(offer -> getOfferDTO(localDate, offer))
                .toList();
    }

    @NotNull
    private OfferDTO getOfferDTO(LocalDate localDate, Offer offer) {
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setId(offer.getId());
        offerDTO.setOrigin(offer.getOrigin());
        offerDTO.setDestination(offer.getDestination());
        offerDTO.setCabinClass(offer.getCabinClass());
        offerDTO.setFlightId(offer.getFlightId());
        offerDTO.setTravelDate(localDate);
        return offerDTO;
    }


}