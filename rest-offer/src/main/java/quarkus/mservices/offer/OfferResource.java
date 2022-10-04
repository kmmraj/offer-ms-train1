package quarkus.mservices.offer;

import io.quarkus.security.Authenticated;
import io.quarkus.security.UnauthorizedException;
import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.hibernate.annotations.Parameter;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.cache.NoCache;
import quarkus.mservices.offer.repository.OfferRepository;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.persistence.EntityManager;
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


    @Inject
    EntityManager em;
    @ConfigProperty(name = "offer.default.travel.days", defaultValue = "30")
    int defaultTravelDays;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/offers")
    @NoCache

    @Authenticated
    public List<Offer> getOffers() {
        //validateScope("offer:read");

        OfferRepository offerRepository = new OfferRepository();
        return offerRepository.getOffersByFlightId("BCN", "MAD");

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