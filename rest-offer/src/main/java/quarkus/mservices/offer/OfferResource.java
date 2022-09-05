package quarkus.mservices.offer;

import io.quarkus.security.Authenticated;
import io.quarkus.security.UnauthorizedException;
import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Tag(name = "Resource for Offer APIs")
@Schema(description = "OfferResource API")
@Path("/api")
public class OfferResource {

    @Inject
    Logger logger;

    @Inject
    SecurityIdentity securityIdentity;
// TODO : 2 -- Check this

    @Inject
    JsonWebToken jwt;

//    @Inject
//    @Claim("scope")
//    Set<String> scopes;

    @ConfigProperty(name = "offer.default.travel.days", defaultValue = "30")
    int defaultTravelDays;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/offers")
    @NoCache

    @Authenticated
    public List<Offer> getOffers() {
        validateScope("offer:read");

        Offer offerOne = new Offer();
        offerOne.setId(UUID.randomUUID().toString().substring(0, 8));
        offerOne.setCabinClass(CabinClassEnum.ECONOMY);
        offerOne.setDestination("MAD");
        offerOne.setFlightId(UUID.randomUUID().toString().substring(0, 5));
        offerOne.setOrigin("BCN");
        offerOne.setDepartureDate(Date.from(Instant.now().plus(defaultTravelDays, ChronoUnit.DAYS)));
        logger.info(" Offer One is:: " + offerOne);
        return List.of(offerOne);
    }

    private void validateScope(String expectedScope) {
        Optional.ofNullable(jwt.getClaim("scope"))
                .map(Object::toString)
                .filter(s -> s.contains(expectedScope))
                .stream().findFirst()
                .orElseThrow(() -> new UnauthorizedException("User is not authorized to access this resource"));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    @NoCache
    @Path("/user")
    public SecurityIdentity getUserInfo() {
        logger.info(" getUserInfo called: ");
        return securityIdentity;
    }
}