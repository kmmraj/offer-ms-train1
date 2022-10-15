package quarkus.mservices.offer;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RegisterRestClient(configKey = "offer-price.proxy")
@Path("/api")
public interface OfferPriceProxy {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "OfferPrice by offerId", description = "Get OfferPrice by offerId")
    @Path("/offer-price/offer/{offerId}")
    public OfferPriceDTO getOfferPrice(@PathParam("offerId") String offerId);
}
