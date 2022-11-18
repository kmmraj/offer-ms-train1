package quarkus.mservices.offer;

import io.grpc.Metadata;
import io.quarkus.grpc.GrpcClient;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.graalvm.collections.Pair;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.cache.NoCache;
import quarkus.mservices.offer.repository.Offer;
import quarkus.mservices.offer.repository.OfferRepository;
import quarkus.mservices.offerprice.OfferPriceRequest;
import quarkus.mservices.offerprice.OfferPriceResponse;
import quarkus.mservices.offerprice.OfferPriceServiceInterfaceGrpc;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Tag(name = "Resource for Offer APIs")
@Schema(description = "OfferResource API")
@Path("/api")
public class OfferResource {


    @Inject
    Logger logger;
    @Inject
    OfferRepository offerRepository;

    @Inject
    @RestClient
    OfferPriceProxy offerPriceProxy;

    @GrpcClient("offerprice")
    OfferPriceServiceInterfaceGrpc.OfferPriceServiceInterfaceBlockingStub blockingOfferPriceService;


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
    @Fallback(fallbackMethod = "getOfferPriceFallBack")
    //  @Timeout(value = 5000, unit = ChronoUnit.MILLIS)
//    @Retry(maxRetries = 5, delay = 100, maxDuration = 5000, jitter = 10)
    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000, successThreshold = 2)
    //@Authenticated
    @PermitAll
    public List<OfferExtendedDTO> getOffers(@PathParam("origin") String origin,
                                            @PathParam("destination") String destination,
                                            @PathParam("travelDate") String travelDate) {
        LocalDate localDate = LocalDate.parse(travelDate, DateTimeFormatter.ISO_LOCAL_DATE);
        logger.info("getOffers with: " + origin + " and " + destination + " and " + localDate);
        List<Offer> offerList = offerRepository
                .getOffersByOriginAndDestinationAndTravelDate(origin, destination, localDate);
        return offerList
                .stream()
//                .map(offer -> Pair.create(offerPriceProxy.getOfferPrice(offer.getId()),offer))
//                .map(offer -> Pair.create(getOfferPriceResponse(offer.getId()), offer))
                .map(offer -> Pair.create(getOfferPriceResponseFallBack(offer.getId()), offer))
                .map(pair -> getOfferExtendedDTO(pair.getLeft(), pair.getRight(), localDate))
                .toList();

    }

    private OfferPriceResponse getOfferPriceResponse(String offerId) {

//        Metadata extraHeaders = new Metadata();
//        extraHeaders.put("bearer", "jwtToken"); // create a JWT token and pass it here
//        return blockingOfferPriceService
//                .withCallCredentials((method, attrs, appExecutor, applier) -> {
//                    //applier.apply(extraHeaders);
//                    return null;
//                })
//                .getOfferPrice(OfferPriceRequest.newBuilder().setOfferId(offerId).build());

        return blockingOfferPriceService
                .getOfferPrice(
                        OfferPriceRequest
                                .newBuilder()
                                .setOfferId(offerId)
                                .build()
                );
    }

    private OfferPriceResponse getOfferPriceResponseFallBack(String offerId) {

//        Metadata extraHeaders = new Metadata();
//        extraHeaders.put("bearer", "jwtToken"); // create a JWT token and pass it here
//        return blockingOfferPriceService
//                .withCallCredentials((method, attrs, appExecutor, applier) -> {
//                    //applier.apply(extraHeaders);
//                    return null;
//                })
//                .getOfferPrice(OfferPriceRequest.newBuilder().setOfferId(offerId).build());

        return OfferPriceResponse
                .newBuilder()
                .setPrice("100")
                .setOfferId(offerId)
                .setCurrency("EUR")
                .setId("1")
                .build();
    }


    //    @GET
//    @Produces(MediaType.APPLICATION_JSON)
    @Path("/offers/orig/{origin}/dest/{destination}/date/{travelDate}")
    public List<OfferExtendedDTO> getOfferPriceFallBack(@PathParam("origin") String origin,
                                                        @PathParam("destination") String destination,
                                                        @PathParam("travelDate") String travelDate) {
        LocalDate localDate = LocalDate.parse(travelDate, DateTimeFormatter.ISO_LOCAL_DATE);
        logger.info("getOffers with: " + origin + " and " + destination + " and " + localDate);
        List<Offer> offerList = offerRepository.getOffersByOriginAndDestinationAndTravelDate(origin, destination, localDate);
        return offerList
                .stream()
                .map(offer -> Pair.create(new OfferPriceDTO("1", offer.getId(), BigDecimal.valueOf(50.0), "EUR"), offer))
                .map(pair -> getOfferExtendedDTO(pair.getLeft(), pair.getRight(), localDate))
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

    @NotNull
    private OfferExtendedDTO getOfferExtendedDTO(OfferPriceDTO offerPriceDTO, Offer offer, LocalDate localDate) {
        OfferExtendedDTO offerExtendedDTO = new OfferExtendedDTO();
        offerExtendedDTO.setId(offer.getId());
        offerExtendedDTO.setOrigin(offer.getOrigin());
        offerExtendedDTO.setDestination(offer.getDestination());
        offerExtendedDTO.setCabinClass(offer.getCabinClass());
        offerExtendedDTO.setFlightId(offer.getFlightId());
        offerExtendedDTO.setTravelDate(localDate);
        offerExtendedDTO.setPrice(offerPriceDTO.getPrice());
        offerExtendedDTO.setCurrency(offerPriceDTO.getCurrency());
        return offerExtendedDTO;
    }

    private OfferExtendedDTO getOfferExtendedDTO(OfferPriceResponse offerPriceResponse, Offer offer, LocalDate localDate) {
        OfferExtendedDTO offerExtendedDTO = new OfferExtendedDTO();
        offerExtendedDTO.setId(offer.getId());
        offerExtendedDTO.setOrigin(offer.getOrigin());
        offerExtendedDTO.setDestination(offer.getDestination());
        offerExtendedDTO.setCabinClass(offer.getCabinClass());
        offerExtendedDTO.setFlightId(offer.getFlightId());
        offerExtendedDTO.setTravelDate(localDate);
        offerExtendedDTO.setPrice(new BigDecimal(offerPriceResponse.getPrice()));
        offerExtendedDTO.setCurrency(offerPriceResponse.getCurrency());
        return offerExtendedDTO;
    }

    public OfferPriceDTO getOfferPriceFallBack(String offerId) {
        return new OfferPriceDTO("1", offerId, new java.math.BigDecimal(20), "EUR");
    }
}