package quarkus.mservices.offerprice;

import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;
import org.jboss.logging.Logger;
import quarkus.mservices.offerprice.OfferPriceRequest;
import quarkus.mservices.offerprice.OfferPriceResponse;
import quarkus.mservices.offerprice.OfferPriceServiceInterface;
import quarkus.mservices.offerprice.OfferPriceServiceInterfaceGrpc;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class OfferPriceEndpoint {

    @Inject
    Logger logger;

    @GrpcClient("offerprice")
    OfferPriceServiceInterface offerPriceService;

    @GrpcClient("offerprice")
    OfferPriceServiceInterfaceGrpc.OfferPriceServiceInterfaceBlockingStub  blockingOfferPriceService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //  @Operation(summary = "OfferPrice by offerId", description = "Get OfferPrice by offerId")
    @Path("/offer-price/react/offer/{offerId}")
    public Uni<OfferPriceResponse> getOfferPriceReact(@PathParam("offerId") String offerId) {
        logger.info("getOfferPrice with: " + offerId);
        return offerPriceService.getOfferPrice(OfferPriceRequest.newBuilder().setOfferId(offerId).build());

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //  @Operation(summary = "OfferPrice by offerId", description = "Get OfferPrice by offerId")
    @Path("/offer-price/offer/{offerId}")
    public OfferPriceResponse getOfferPrice(@PathParam("offerId") String offerId) {
        logger.info("getOfferPrice with: " + offerId);
        //return blockingOfferPriceService.getOfferPrice(OfferPriceRequest.newBuilder().setOfferId(offerId).build());
        return blockingOfferPriceService.getOfferPrice(OfferPriceRequest.newBuilder().setOfferId(offerId).build());
    }
}
